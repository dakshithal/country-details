import { Box, List, ListItem, ListItemButton, Typography } from "@mui/material"

export interface CountryListItem {
  name: string;
  country_code: string;
}

export interface CountryListProps {
  countries: CountryListItem[];
  setSelectedCountry: ((selectedCountry: CountryListItem) => void);
}

export const CountryList = ({ countries, setSelectedCountry }: CountryListProps) => {
  return (
    <Box id="country-list" style={{width: "100%"}}>
      <Typography variant="h2">List of Countries</Typography> 
      <List>
      {
        countries.map((country) => {
          return (
            <ListItem key={country.country_code} onClick={() => setSelectedCountry(country)}>
              <ListItemButton>{country.name}</ListItemButton>
            </ListItem>
          );
        })
      }
      </List>
    </Box>
  )
}