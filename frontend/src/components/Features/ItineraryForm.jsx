import React, { useState } from "react";
import GeosearchInput from "../UI/GeosearchInput";
import { FiFilter } from "react-icons/fi";
import { HiOutlineSwitchVertical } from "react-icons/hi";
import { useLazyQuery } from "@apollo/client";
import { COMPUTE_ITINERARY } from "../../graphql/queries";

const ItineraryForm = ({ setStart, setEnd, setShowFilter, user, setResults, selectedTransport }) => {
  const [startAddress, setStartAddress] = useState("");
  const [endAddress, setEndAddress] = useState("");
  const [startCoords, setStartCoords] = useState(null);
  const [endCoords, setEndCoords] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [computeItinerary] = useLazyQuery(COMPUTE_ITINERARY);

  const handleSwap = (e) => {
    e.preventDefault();
    setStartAddress(endAddress);
    setEndAddress(startAddress);
    setStartCoords(endCoords);
    setEndCoords(startCoords);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);

    if (!startCoords || !endCoords) {
      alert("Veuillez entrer une adresse de départ et une adresse d'arrivée valides.");
      return;
    }

    setStart([startAddress, startCoords]);
    setEnd([endAddress, endCoords]);
    setLoading(true);

    const transportTypes = Object.keys(selectedTransport).filter(key => selectedTransport[key]);

    const results = [];

    for (const vehicleType of transportTypes) {
      const variables = {
        input: {
          sourceText: startAddress,
          targetText: endAddress,
          sourceLat: startCoords.lat,
          sourceLon: startCoords.lng,
          targetLat: endCoords.lat,
          targetLon: endCoords.lng,
          vehicleType,
          userID: parseInt(user?.id) || null,
        },
      };

      try {
        const { data } = await computeItinerary({ variables });
        if (data?.computeItinerary) {
          const lats = data.computeItinerary.points.lats;
          const lons = data.computeItinerary.points.lons;

          const routePoints = lats.map((lat, index) => ({
            lat,
            lng: lons[index],
          }));
          results.push({ ...data.computeItinerary, vehicleType, routePoints });

        } else {
          results.push({
            id: `dummy-${vehicleType}`,
            distance: 0,
            emissions: 0,
            vehicleType,
            startCoordinates: startCoords,
            endCoordinates: endCoords,
          });
        }
      } catch (err) {
        console.error(`Erreur pour ${vehicleType}:`, err);
        results.push({
          id: `error-${vehicleType}`,
          distance: 0,
          emissions: 0,
          vehicleType,
          startCoordinates: startCoords,
          endCoordinates: endCoords,
        });
      }
    }

    setResults(results);
    setLoading(false);
  };


  const toggleFilter = (e) => {
    e.preventDefault();
    setShowFilter((prev) => !prev);
  };

  return (
    <>
      {/* Le reste du composant reste inchangé */}
      <form className="space-y-4 relative">
        <div className="flex items-center">
          <div className="flex-1 space-y-3">
            <GeosearchInput
              address={startAddress}
              setAddress={setStartAddress}
              setLocation={setStartCoords}
              placeholder="Adresse de départ"
            />
            <GeosearchInput
              address={endAddress}
              setAddress={setEndAddress}
              setLocation={setEndCoords}
              placeholder="Adresse d'arrivée"
            />
          </div>
          <button
            className="bg-transparent ms-1"
            onClick={handleSwap}
            aria-label="Inverser départ et arrivée"
          >
            <HiOutlineSwitchVertical size={22} />
          </button>
        </div>

        <div className="flex justify-between mt-2">
          <button
            onClick={handleSubmit}
            className="w-50 mx-auto border-2 border-[#D9D9D9] px-5 py-1 rounded-[8px]"
            disabled={loading}
          >
            {loading ? "Calcul..." : "Aller"}
          </button>
          <button
            onClick={toggleFilter}
            className="absolute right-5 mt-1 border-2 border-[#D9D9D9] px-1 rounded-[8px]"
            aria-label="Filtrer les transports"
          >
            <FiFilter size={22} />
          </button>
        </div>
      </form>

      {loading && (
        <div className="mt-4 p-3 bg-blue-50 rounded-lg text-blue-800 text-sm">
          Calcul des itinéraires en cours...
        </div>
      )}

      {error && (
        <div className="mt-4 p-3 bg-red-50 rounded-lg text-red-800 text-sm">
          {error}
        </div>
      )}
    </>
  );
};

export default ItineraryForm;