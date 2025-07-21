import React, { useState, useEffect } from "react";
import { OpenStreetMapProvider } from 'leaflet-geosearch';

const GeosearchInput = ({ address, setAddress, setLocation, placeholder }) => {
  const [provider, setProvider] = useState(null);
  const [suggestions, setSuggestions] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    const osmProvider = new OpenStreetMapProvider({
      params: {
        countrycodes: 'fr'
      }
    });
    setProvider(osmProvider);
  }, []);

  // Effet de debounce natif
  useEffect(() => {
    if (searchTerm.length <= 2) {
      setSuggestions([]);
      return;
    }

    // Créer un timer pour retarder la recherche
    const timerId = setTimeout(async () => {
      if (provider) {
        try {
          setIsLoading(true);
          const results = await provider.search({ query: searchTerm });
          setSuggestions(results);
        } catch (error) {
          console.error('Geocoding error:', error);
        } finally {
          setIsLoading(false);
        }
      }
    }, 500);

    // Nettoyer le timer si le composant se démonte ou si le terme change
    return () => clearTimeout(timerId);
  }, [searchTerm, provider]);

  const handleChange = (e) => {
    const value = e.target.value;
    setAddress(value);
    setSearchTerm(value);
  };

  const handleSelect = (suggestion) => {
    if (suggestion && suggestion.x && suggestion.y) {
      setAddress(suggestion.label);
      setLocation({
        lat: suggestion.y,
        lng: suggestion.x
      });
      setSuggestions([]);
      setSearchTerm('');
    }
  };

  return (
    <div className="relative">
      <input type="text" className="w-full p-2 border-2 border-[#D9D9D9] rounded focus:outline-none" 
      value={address} onChange={handleChange} placeholder={placeholder} 
      />

      {address && (
        <button type="button" onClick={() => setAddress('')} className="absolute bg-[#ffffff] right-2 top-1/2 transform -translate-y-1/2"
        aria-label="Effacer l'adresse">
          ✕
        </button>
      )}

      {isLoading && (
        <div className="absolute text-sm text-gray-500 mt-1">
          Recherche en cours...
        </div>
      )}

      {suggestions.length > 0 && (
        <ul className="absolute left-0 right-0 bg-white border border-gray-300 mt-1 max-h-40 overflow-y-auto z-50">
          {suggestions.map((suggestion, index) => (
            <li key={index} onClick={() => handleSelect(suggestion)} className="p-2 cursor-pointer hover:bg-gray-200">
              {suggestion.label}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default GeosearchInput;