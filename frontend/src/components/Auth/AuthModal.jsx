import { useState } from 'react';
import CloseButton from './CloseButton';
import BackButton from './BackButton';
import { useMutation } from '@apollo/client';
import { REGISTER_MUTATION, LOGIN_MUTATION } from '../../graphql/queries';

const AuthModal = ({ onClose, onLogin }) => {
  const [view, setView] = useState('choice');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const [loginEmail, setLoginEmail] = useState('');
  const [loginPassword, setLoginPassword] = useState('');

  const [registerName, setRegisterName] = useState('');
  const [registerEmail, setRegisterEmail] = useState('');
  const [registerPassword, setRegisterPassword] = useState('');

  const [registerUser, { loading: registerLoading }] = useMutation(REGISTER_MUTATION);
  const [loginUser, { loading: loginLoading }] = useMutation(LOGIN_MUTATION);

  const handleChangeView = (newView) => {
    setError('');
    setSuccess('');
    setView(newView);
  };

  const handleLogin = async (e) => {
    e.preventDefault();
    if (!loginEmail || !loginPassword) {
      setError("Veuillez remplir tous les champs.");
      return;
    }

    try {
      const { data } = await loginUser({
        variables: {
          input: {
            login: loginEmail,
            password: loginPassword,
          },
        },
      });

      const token = data.login;
      localStorage.setItem("token", token); 
      
      onLogin && onLogin({ login: loginEmail });
      onClose(); 
      
      window.location.reload(); 
      
    } catch (err) {
      setError(err.message || "Erreur lors de la connexion.");
    }
  };

  const handleRegister = async (e) => {
    e.preventDefault();
    if (!registerName || !registerEmail || !registerPassword) {
      setError("Veuillez remplir tous les champs.");
      return;
    }

    try {
      const { } = await registerUser({
        variables: {
          input: {
            login: registerEmail,
            password: registerPassword,
            type: 'UTILISATEUR',
          },
        },
      });

      setSuccess("Inscription réussie ✅ Connectez-vous !");
      setView('login');
    } catch (err) {
      setError(err.message || "Erreur lors de l'inscription.");
    }
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-40 z-[9999] flex items-center justify-center backdrop-blur-sm">
      <div className="bg-white rounded-2xl p-8 w-[420px] shadow-xl relative animate-fade-in">
        {(view === 'login' || view === 'register') && (
          <BackButton onClick={() => handleChangeView('choice')} />
        )}
        <CloseButton onClick={onClose} />

        {view === 'choice' && (
          <div className="space-y-5 text-center">
            <br />
            <h2 className="text-2xl font-semibold text-gray-800">Bienvenue 👋</h2>
            <p className="text-gray-500 text-sm">Veuillez choisir une action</p>
            <div className="flex flex-col gap-3 mt-4">
              <button
                onClick={() => handleChangeView('login')}
                className="w-full py-2 bg-blue-600 text-white font-medium rounded-md hover:bg-blue-700 transition"
              >
                Se connecter
              </button>
              <button
                onClick={() => handleChangeView('register')}
                className="w-full py-2 bg-gray-100 text-gray-800 font-medium rounded-md hover:bg-gray-200 transition"
              >
                S'inscrire
              </button>
            </div>
          </div>
        )}

        {view === 'login' && (
          <div className="space-y-4 mt-2">
            <br />
            <h2 className="text-xl font-semibold text-center text-gray-800">Connexion</h2>
            {error && <p className="text-red-500 text-sm text-center">{error}</p>}
            {success && <p className="text-green-500 text-sm text-center">{success}</p>}
            <input
              type="email"
              placeholder="Email"
              value={loginEmail}
              onChange={(e) => setLoginEmail(e.target.value)}
              className="w-full px-3 py-2 border border-gray-300 rounded-md"
            />
            <input
              type="password"
              placeholder="Mot de passe"
              value={loginPassword}
              onChange={(e) => setLoginPassword(e.target.value)}
              className="w-full px-3 py-2 border border-gray-300 rounded-md"
            />
            <button
              onClick={handleLogin}
              className="w-full py-2 bg-blue-600 text-white font-medium rounded-md hover:bg-blue-700"
            >
              {loginLoading ? "Connexion..." : "Connexion"}
            </button>
          </div>
        )}

        {view === 'register' && (
          <div className="space-y-4 mt-2">
            <br />
            <h2 className="text-xl font-semibold text-center text-gray-800">Inscription</h2>
            {error && <p className="text-red-500 text-sm text-center">{error}</p>}
            <input
              type="text"
              placeholder="Nom complet"
              value={registerName}
              onChange={(e) => setRegisterName(e.target.value)}
              className="w-full px-3 py-2 border border-gray-300 rounded-md"
            />
            <input
              type="email"
              placeholder="Email"
              value={registerEmail}
              onChange={(e) => setRegisterEmail(e.target.value)}
              className="w-full px-3 py-2 border border-gray-300 rounded-md"
            />
            <input
              type="password"
              placeholder="Mot de passe"
              value={registerPassword}
              onChange={(e) => setRegisterPassword(e.target.value)}
              className="w-full px-3 py-2 border border-gray-300 rounded-md"
            />
            <button
              onClick={handleRegister}
              className="w-full py-2 bg-green-600 text-white font-medium rounded-md hover:bg-green-700"
            >
              {registerLoading ? "Inscription..." : "S'inscrire"}
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default AuthModal;
