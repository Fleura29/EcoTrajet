import { FaArrowLeft } from 'react-icons/fa';

const BackButton = ({ onClick }) => (
  <button
    onClick={onClick}
    className="absolute top-3 left-3 text-gray-400 hover:text-gray-600 text-[18px]"
    aria-label="Retour"
  >
    <FaArrowLeft />
  </button>
);

export default BackButton;
