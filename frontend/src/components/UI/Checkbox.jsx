import React from 'react';

const Checkbox = ({ label, checked, onChange }) => {

  return (
    <label className="flex items-center cursor-pointer">
      <input type="checkbox" checked={checked} onChange={onChange} className="mr-2" />
      {label}
    </label>
  );
};

export default Checkbox;