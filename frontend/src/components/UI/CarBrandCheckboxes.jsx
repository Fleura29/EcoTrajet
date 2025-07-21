import React, { useState } from "react";
import Checkbox from './Checkbox';

const CarBrandCheckboxes = ({ onSelectionChange }) => {
    const [selectedCarBrands, setSelectedCarBrands] = useState({});

    const carBrands = [
        { key: "renault", label: "Renault" },
        { key: "mercedes", label: "Mercedes" },
        { key: "peugeot", label: "Peugeot" },
        { key: "nissan", label: "Nissan" },
        { key: "bmw", label: "BMW" },
        { key: "fiat", label: "FIAT" }
    ];

    const handleBrandChange = (key) => {
        const newCarSelection = { 
            ...selectedCarBrands, 
            [key]: !selectedCarBrands[key] 
        };
        setSelectedCarBrands(newCarSelection);
        onSelectionChange(newCarSelection);
    };
      
    return (
        <div>
            <h2 className="font-semibold mb-2 text-black">Marque</h2>
            <div className="grid grid-cols-2 gap-2">
                {carBrands.map(brand => (
                    <Checkbox key={brand.key} label={brand.label} checked={!!selectedCarBrands[brand.key]}
                        onChange={() => handleBrandChange(brand.key)} />
                ))}
            </div>
        </div>
    );
};

export default CarBrandCheckboxes;
