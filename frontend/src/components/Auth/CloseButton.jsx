import { FaTimes } from 'react-icons/fa';

const CloseButton = ({ onClick }) => (
  <button
    onClick={onClick}
    className="absolute top-3 right-3 text-gray-400 hover:text-gray-600 text-[18px]"
    aria-label="Fermer"
  >
    <FaTimes />
  </button>
);

export default CloseButton;
