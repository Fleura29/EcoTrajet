import React, { useState, useEffect } from "react";
import GeosearchInput from '../UI/GeosearchInput';
import CO2Input from '../UI/CO2Input';
import { FiFilter } from "react-icons/fi";

const CO2Form = ({ setStart, setCO2, setShowFilter }) => {
    const [startAddress, setStartAddress] = useState("");
    const [CO2Value, setCO2Value] = useState("");

    useEffect(() => {
        setCO2(CO2Value);
    }, [CO2Value, setCO2]);

    const handleSubmit = (e) => {
        e.preventDefault();
        setCO2(CO2Value);
    };

    const toggleFilter = (e) => {
        e.preventDefault();
        setShowFilter(prev => !prev);
    };

    return (
        <form className="space-y-3" onSubmit={handleSubmit}>
            <GeosearchInput address={startAddress} setAddress={setStartAddress} setLocation={setStart} placeholder="Adresse de départ" />
            <CO2Input CO2Value={CO2Value} setCO2Value={setCO2Value} />
            <div className="flex justify-between mt-2">
                <button onClick={handleSubmit} className="w-50 mx-auto border-2 border-[#D9D9D9] px-5 py-1 rounded-[8px]">
                    Aller
                </button>
                <button onClick={toggleFilter} className="absolute right-5 mt-1 border-2 border-[#D9D9D9] px-1 rounded-[8px]"
                    aria-label="Filtrer les transports">
                    <FiFilter size={22} />
                </button>
            </div>
        </form>
    );
};

export default CO2Form;
