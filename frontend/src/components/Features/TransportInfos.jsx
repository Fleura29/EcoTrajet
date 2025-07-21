import React, { useEffect, useState } from "react";
import TransportResultCard from "../UI/TransportResultCard";

const TransportInfos = ({ user, results, start, end, onSelectTransport }) => {
  const [favorites, setFavorites] = useState([]);
  
  const shorten = (label) => label?.split(",").slice(0, 2).join(",") ?? "Inconnue";
  const startLabel = shorten(start?.[0]);
  const endLabel = shorten(end?.[0]);
  
  useEffect(() => {
    if (!user) {
      setFavorites([]);
      return;
    }
    const key = `favorites_${user.login}`;
    const stored = localStorage.getItem(key);
    setFavorites(stored ? JSON.parse(stored) : []);
  }, [user]);

 
  const handleToggleFavorite = (result) => {
    if (!user) return;

    const key = `favorites_${user.login}`;

    const newFavorite = {
      type: result.vehicleType,
      origin: startLabel,
      destination: endLabel,
      distance: Number((result.distance).toFixed(2)),
      co2: Number(result.emissions),
      time: result.time
    };

    const isAlreadyFavorite = favorites.some(
      (fav) =>
        fav.type === newFavorite.type &&
        fav.origin === newFavorite.origin &&
        fav.destination === newFavorite.destination &&
        fav.distance === newFavorite.distance &&
        fav.co2 === newFavorite.co2 &&
        fav.time === newFavorite.time
    );

    let updatedFavorites;
    if (isAlreadyFavorite) {
      updatedFavorites = favorites.filter(
        (fav) =>
          !(
            fav.type === newFavorite.type &&
            fav.origin === newFavorite.origin &&
            fav.destination === newFavorite.destination &&
            fav.distance === newFavorite.distance &&
            fav.co2 === newFavorite.co2 && 
            fav.time === newFavorite.time
          )
      );
    } else {
      updatedFavorites = [...favorites, newFavorite];
    }

    setFavorites(updatedFavorites);
    localStorage.setItem(key, JSON.stringify(updatedFavorites));
  };

  return (
    <div>
      {results.map((result) => {
        const favObj = {
          type: result.vehicleType,
          origin: startLabel,
          destination: endLabel,
          distance: Number((result.distance).toFixed(2)),
          co2: Number(result.emissions),
        };

        const isFavorite = favorites.some(
          (fav) =>
            fav.type === favObj.type &&
            fav.origin === favObj.origin &&
            fav.destination === favObj.destination &&
            fav.distance === favObj.distance &&
            fav.co2 === favObj.co2
        );

        return (
           <div key={result.id}>
          <TransportResultCard
            type={result.vehicleType}
            distance={result.distance}
            co2={result.emissions}
            time={result.time} 
            origin={startLabel}
            destination={endLabel}
            user={user}
            isFavorite={isFavorite}
            onFavorite={() => handleToggleFavorite(result)}
            
            onSelectTransport={() => 
            onSelectTransport(result, result.vehicleType)
            }
            route={{
              start: result.startCoordinates,
              end: result.endCoordinates
            }} 
          />
          
    </div>

        );
      })}
    </div>
  );
};

export default TransportInfos;
