import React, { useEffect, useState } from "react";

const UserModal = ({ user, onLogout }) => {
  const [favorites, setFavorites] = useState([]);

  useEffect(() => {
    if (user) {
      const stored = localStorage.getItem(`favorites_${user.login}`);
      setFavorites(stored ? JSON.parse(stored) : []);
    }
  }, [user]);

  // Écoute le custom event "favoritesChanged"
  useEffect(() => {
    const refreshFavorites = () => {
      const stored = localStorage.getItem(`favorites_${user.login}`);
      setFavorites(stored ? JSON.parse(stored) : []);
    };

    refreshFavorites(); // initial

    window.addEventListener("favoritesChanged", refreshFavorites);
    return () => window.removeEventListener("favoritesChanged", refreshFavorites);
  }, [user]);

  const formatTime = (seconds) => {
    const totalMinutes = Math.round(seconds / 60);
    const h = Math.floor(totalMinutes / 60);
    const m = totalMinutes % 60;
    return h > 0 ? `${h} h ${m} min` : `${m} min`;
  };
  

  return (
    <div className="absolute top-16 right-4 z-[9999] bg-white rounded-xl shadow-xl w-72 p-4 animate-fade-in">
      <div className="mb-2">
        <h2 className="text-sm text-gray-700 mb-1">👋 Bienvenue,</h2>
        <p className="font-semibold text-black text-sm">{user.login}</p>
      </div>

      <hr className="my-3" />

      {favorites.length === 0 ? (
        <div className="text-xs text-gray-600 italic mb-4">
          Vos trajets favoris s’afficheront ici.
        </div>
      ) : (
        <div className="text-xs text-gray-800 mb-4">
          <h3 className="text-sm font-semibold mb-2">✨ Trajets favoris :</h3>
          {favorites.map((fav, index) => (
            <div key={index} className="mb-2">
              ▸ <strong>{fav.type}</strong><br />
              🏁 <strong>De :</strong> {fav.origin}<br />
              🎯 <strong>À :</strong> {fav.destination}<br />
              📏 <strong>Distance :</strong> {fav.distance} km<br />
              🌿 <strong>CO₂ :</strong> {Number(fav.co2).toFixed(2)} g<br />
              ⏱ <strong>Temps :</strong> {formatTime(fav.time)} 
            </div>
          ))}
        </div>
      )}

      <button
        onClick={onLogout}
        className="w-full py-2 text-sm bg-red-100 text-red-600 rounded-md hover:bg-red-200 transition"
      >
        Déconnexion
      </button>
    </div>
  );
};

export default UserModal;
