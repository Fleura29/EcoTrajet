// TransportResultCard.jsx
import {
  FaWalking,
  FaTrain,
  FaBus,
  FaCar,
  FaBicycle,
} from "react-icons/fa";
import { MdElectricScooter } from "react-icons/md";
import { AiOutlineHeart, AiFillHeart } from "react-icons/ai";
import { BiRecycle } from "react-icons/bi";

const transportIcons = {
  pedestrian: <FaWalking />,
  car: <FaCar />,
  bicycle: <FaBicycle />,

};

const formatLabel = (type) => {
  const labels = {
    pedestrian: "À pied",
    car: "Voiture",
    bicycle: "Vélo électrique",
  };
  return labels[type] || type;
};

const formatTime = (seconds) => {
  const totalMinutes = Math.round(seconds / 60);
  const h = Math.floor(totalMinutes / 60);
  const m = totalMinutes % 60;
  return h > 0 ? `${h} h ${m} min` : `${m} min`;
};


const TransportResultCard = ({
  type,
  distance,
  co2,
  time,
  user,
  isFavorite,
  onFavorite,
  onSelectTransport,
  route
}) => {
  
  const distanceInKm = Number(parseFloat(distance).toFixed(2));
  const emission = Number(parseFloat(co2).toFixed(2));

  const handleClick = () => {
    if (onSelectTransport) {
      onSelectTransport();
    }
  };

  return (
    <div
  onClick={handleClick}
  className="flex items-start gap-4 bg-white rounded-2xl shadow-md px-4 py-2 mb-3 transition-transform hover:scale-[1.015] hover:shadow-lg cursor-pointer"
>
      <div className="text-3xl text-blue-600 mt-1">
        {transportIcons[type]}
      </div>
      <div className="flex-1 text-gray-800 text-sm">
        <div className="flex justify-between items-center">
          <div className="font-semibold text-lg">{formatLabel(type)}</div>
          {user && (
            <button
              onClick={onFavorite}
              className="rounded-full p-1 hover:bg-gray-100 transition"
              title={isFavorite ? "Retirer des favoris" : "Ajouter aux favoris"}
            >
              {isFavorite ? (
                <AiFillHeart className="text-red-500" size={20} />
              ) : (
                <AiOutlineHeart className="text-gray-500" size={20} />
              )}
            </button>


          )}
        </div>
        <div className="text-gray-600 mt-1 leading-5">
          ▸ <strong>Distance :</strong> {distanceInKm} km
          <br />
          ▸ <strong>CO₂ :</strong> {emission} g
          <br />
          ▸ <strong>Temps :</strong> {formatTime(time)}
        </div>
      </div>
    </div>
  );
};

export default TransportResultCard;
