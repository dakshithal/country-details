import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App';

test('renders List of Countries header', () => {
  render(<App />);
  const listOfCountriesTitle = screen.getByText(/List of Countries/i);
  expect(listOfCountriesTitle).toBeInTheDocument();
});
