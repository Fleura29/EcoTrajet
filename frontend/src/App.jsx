import { useState, useEffect } from 'react';
import './App.css';
import Map from './components/Map/Map';
import ItineraryForm from './components/Features/ItineraryForm';
import TransportForm from './components/Features/TransportForm';
import TransportInfos from './components/Features/TransportInfos';
import CO2Form from './components/Features/CO2Form';
import ModesInput from './components/UI/ModesInput';
import AuthManager from './components/Auth/AuthManager';

function App() {
  const [start, setStart] = useState(null);
  const [end, setEnd] = useState(null);
  const [activeTab, setActiveTab] = useState('Itinéraire');
  const [CO2, setCO2] = useState(null);
  const [resetKey, setResetKey] = useState(0)
  const [selectedTransportType, setSelectedTransportType] = useState(null);
  const [selectedRoute, setSelectedRoute] = useState(null);

  const transportOptions = [
    { key: "car", label: "Voiture" },
    { key: "bicycle", label: "Vélo " },
    { key: "pedestrian", label: "Marche" },
  ];
  const [selectedTransport, setSelectedTransport] = useState(
    Object.fromEntries(transportOptions.map(option => [option.key, true]))
  );
  const [selectedCarBrands, setSelectedCarBrands] = useState({});
  const [showFilter, setShowFilter] = useState(false);
  const [results, setResults] = useState([]);
  const [user, setUser] = useState(() => {
    const token = localStorage.getItem("token");
    const mockUser = localStorage.getItem("mockUser");
    return token && mockUser ? JSON.parse(mockUser) : null;
  });
  
  const handleTabChange = (newTab) => {
    setStart(null);
    setEnd(null);
    setCO2(null);
    setActiveTab(newTab);
    setShowFilter(false);
    setResults([]);
    setSelectedRoute(null);
    setResetKey(prev => prev + 1);
  };

  const handleSelectTransport = (result, transportType) => {
    if (!result.routePoints || result.routePoints.length < 2) {
      console.warn("⚠️ Pas de routePoints valides");
      return;
    }

    setSelectedTransportType(transportType);
    setSelectedRoute(result.routePoints);
    setResetKey(prev => prev + 1);
  };

  return (
    <div>
      <AuthManager user={user} setUser={setUser} />
      
      <div className="flex absolute top-2 left-2 z-1001">
        <div className="flex flex-col w-[300px] h-[calc(100vh-1rem)]">
          <div className="p-4 bg-[#EEEEEE] rounded-t-[8px]">
            <ModesInput activeTab={activeTab} setActiveTab={handleTabChange} />
            
            {activeTab === 'Itinéraire' && (
              <ItineraryForm 
                setStart={setStart}
                setEnd={setEnd}
                setShowFilter={setShowFilter}
                selectedTransport={selectedTransport}
                user={user}
                setResults={setResults}
              />
            )}
            
            {activeTab === 'Global' && (
              <CO2Form
                setStart={setStart}
                setCO2={setCO2}
                setShowFilter={setShowFilter}
              />
            )}
          </div>
          
          {showFilter && (
            <div className="p-4 bg-[#F8F8F8] rounded-b-[8px] shadow-lg overflow-y-auto flex-grow">
              <TransportForm 
                selectedTransport={selectedTransport}
                setSelectedTransport={setSelectedTransport} 
                setSelectedCarBrands={setSelectedCarBrands} 
              />
            </div>
          )}

          {!showFilter && results.length > 0 && (
            <div className="p-4 bg-[#F8F8F8] rounded-b-[8px] shadow-lg overflow-y-auto flex-grow">
              <TransportInfos 
               user={user}
               results={results} 
               start={start} 
               end={end} 
               onSelectTransport={handleSelectTransport}
               />
            </div>
          )}
        </div>
      </div>
      
      <Map 
        resetKey={resetKey} 
        routePoints={selectedRoute}
      />
    </div>
  );
}

export default App;