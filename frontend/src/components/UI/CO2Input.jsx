import React from 'react';

const CO2Input = ({ CO2Value, setCO2Value }) => {

  const handleChange = (e) => {
    const value = e.target.value;
    setCO2Value(value);
  };

  return (
    <div className="relative">
      <input type="number" className="w-full p-3 text-xs border-2 border-[#D9D9D9] rounded focus:outline-none"
      placeholder="Consommation totale en CO2 (en kg)" value={CO2Value} onChange={handleChange} 
      />
    </div>
  );
};

export default CO2Input;