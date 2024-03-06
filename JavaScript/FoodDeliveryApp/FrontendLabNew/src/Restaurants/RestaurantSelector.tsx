
import React, { useState, useEffect } from 'react';
import { MenuItem, Select } from '@mui/material';
import { SelectChangeEvent } from '@mui/material/Select';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';


interface Restaurant {
  id: number;
  name: string;
  address: string;
}

interface RestaurantSelectorProps {
}

const RestaurantSelector: React.FC<RestaurantSelectorProps> = () => {
    //const [selectedRestaurantInfo, setSelectedRestaurantInfo] = useState<Restaurant | null>(null);
    //const navigate = useNavigate();

  const [restaurantList, setRestaurantList] = useState<Restaurant[]>([]);
  const [selectedRestaurant, setSelectedRestaurant] = useState<number | ''>('');

  useEffect(() => {
    const fetchRestaurants = async () => {
      const restaurants = await getRestaurantList();
      setRestaurantList(restaurants);
    };

    fetchRestaurants();
  }, []); 


    const getRestaurantList = async () => {
      try {
        const response = await axios.get('http://localhost:3000');
        return response.data;
      } catch (error) {
        console.error('Error fetching restaurants', error);
        return [];
      }
    };
    

    const handleChange = (event: SelectChangeEvent<number>) => {
        const selectedId = event.target.value as number;
        setSelectedRestaurant(selectedId);
        //navigate(`/menu/${selectedId}`);
    };
  
    return (
      <div>
        <label>Selectați un restaurant:</label>
        <Select value={selectedRestaurant} onChange={handleChange}>
          {restaurantList.map((restaurant) => (
            <MenuItem key={restaurant.id} value={restaurant.id}>
              {restaurant.name}
            </MenuItem>
          ))}
        </Select>
      </div>
    );
  };
  
  export default RestaurantSelector;