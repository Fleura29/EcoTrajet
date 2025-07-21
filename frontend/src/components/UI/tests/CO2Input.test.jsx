import { render, screen, fireEvent } from '@testing-library/react';
import CO2Input from '../CO2Input';
import { describe, it, expect, vi } from 'vitest';

describe('CO2Input', () => {
  it('affiche le champ avec le placeholder correct', () => {
    render(<CO2Input CO2Value="" setCO2Value={() => {}} />);
    expect(
      screen.getByPlaceholderText(/consommation totale en co2/i)
    ).toBeInTheDocument();
  });

  it('appelle setCO2Value quand on tape un chiffre', () => {
    const mockSetCO2Value = vi.fn();
    render(<CO2Input CO2Value="" setCO2Value={mockSetCO2Value} />);

    const input = screen.getByPlaceholderText(/consommation totale en co2/i);
    fireEvent.change(input, { target: { value: '123' } });

    expect(mockSetCO2Value).toHaveBeenCalledWith('123');
  });
});
