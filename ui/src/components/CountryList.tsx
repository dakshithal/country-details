import { List, ListItem, ListItemText, Typography } from "@mui/material"
import { Container } from "@mui/system"

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
    <Container id="country-list">
      <Typography variant="h2">List of Countries</Typography> 
      <List>
      {
        countries.map((country) => {
          return (
            <ListItem key={country.country_code}>
              <ListItemText>{country.name}</ListItemText>
            </ListItem>
          );
        })
      }
      </List>
    </Container>
  )
}