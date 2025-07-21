import { useState } from 'react';
import { FaUser } from 'react-icons/fa';
import AuthModal from './AuthModal';
import UserModal from "./UserModal";


const AuthManager = ({ user, setUser }) => {
  const [showAuthModal, setShowAuthModal] = useState(false);
  const [showUserModal, setShowUserModal] = useState(false);

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("mockUser");
    setUser(null);
    setShowUserModal(false);
  };

  return (
    <>
      <button
        onClick={() => {
          if (user) {  
            setShowUserModal((prev) => !prev); 
          } else {
            setShowAuthModal(true);
          }
        }}
        className="fixed top-4 right-4 z-[9999] bg-white p-2 rounded-full shadow-md hover:bg-gray-100"
      >
        <FaUser size={20} className="text-gray-700" />
      </button>

      {showAuthModal && (
        <AuthModal
          onClose={() => setShowAuthModal(false)}
          onLogin={(user) => {
            localStorage.setItem("mockUser", JSON.stringify(user));
            setUser(user);
            setShowAuthModal(false);
          }}
        />
      )}

      {user && showUserModal && (
        <UserModal user={user} onLogout={handleLogout} />
      )}
    </>
  );
};

export default AuthManager;
