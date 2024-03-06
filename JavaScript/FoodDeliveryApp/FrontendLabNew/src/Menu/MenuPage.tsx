import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';


interface MenuPageProps {
}

const MenuPage: React.FC<MenuPageProps> = () => {
  const { restaurantId } = useParams();
  const [menuItems, setMenuItems] = useState([]);

  useEffect(() => {
    const fetchMenu = async () => {
      const menu = await getMenuForRestaurant(restaurantId);
      setMenuItems(menu);
    };

    fetchMenu();
  }, [restaurantId]); 

  const getMenuForRestaurant = async (restaurantId: string | undefined) => {
    try {
      const response = await axios.get(`http://localhost:8080/Menu/${restaurantId}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching menu for restaurant ${restaurantId}`, error);
      return [];
    }
  };
  
  return <div>Content</div>;

};
export default MenuPage;