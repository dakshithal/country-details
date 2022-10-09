import { Box, List, ListItem, ListItemButton, ListItemIcon, ListItemText, Typography } from '@mui/material'
import FlagIcon from '@mui/icons-material/Flag';
import FlagCircleIcon from '@mui/icons-material/FlagCircle';

export interface CountryListItem {
  name: string;
  country_code: string;
}

export interface CountryListProps {
  countries: CountryListItem[];
  selectedCountry?: CountryListItem;
  setSelectedCountry: ((selectedCountry: CountryListItem) => void);
}

export const CountryList = ({ countries, selectedCountry, setSelectedCountry }: CountryListProps) => {
  return (
    <Box
      id='country-list'
      style={{
        flex: 1,
        marginRight: '20px'
      }}
    >
      <Typography variant='h2'>List of Countries</Typography> 
      <List
      sx={{
        overflow: 'auto',
        maxHeight: 750,
      }}
      >
      {
        countries.map((country) => {
          return (
            <ListItem key={country.name} onClick={() => setSelectedCountry(country)}>
              <ListItemButton>
                <ListItemIcon>
                  {
                    selectedCountry && selectedCountry.country_code === country.country_code
                    ? <FlagIcon color='primary' fontSize='large'/>
                    : <FlagCircleIcon />
                  }
                </ListItemIcon>
                <ListItemText primary={country.name} />
              </ListItemButton>
            </ListItem>
          );
        })
      }
      </List>
    </Box>
  )
}