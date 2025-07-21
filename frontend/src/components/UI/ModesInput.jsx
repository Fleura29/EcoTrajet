import React, { useState } from 'react';

const ModesInput = ({ activeTab, setActiveTab }) => {

  return (
    <div className="flex justify-center pb-3">
      <button className={`px-4 text-xs text-black rounded-s-lg border-2 border-[#BBBBBB] ${activeTab === 'Itinéraire' ? 'bg-grayd9' : 'bg-[#F5F5F5]'}`}
        onClick={() => setActiveTab('Itinéraire')}>
        Itinéraire
      </button>


      <button className={`px-5 text-xs text-black rounded-e-lg border-y-2 border-r-2 border-[#BBBBBB] ${activeTab === 'Global' ? 'bg-grayd9' : 'bg-[#F5F5F5]'}`}
        onClick={() => setActiveTab('Global')}>
        Global
      </button>

    </div>
  );
};

export default ModesInput;