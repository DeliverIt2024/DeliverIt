import React, { useEffect, useState } from "react";
import { NavigationContainer } from "@react-navigation/native";
import { createStackNavigator } from "@react-navigation/stack";
import AsyncStorage from "@react-native-async-storage/async-storage";
import WelcomeScreen from "./components/screens/Welcome";
import LoginScreen from "./components/screens/Login";
import TaskbarNavigator from "./components/Taskbar";

import EditProfileScreen from "./components/screens/EditProfile";
import PastOrdersScreen from "./components/screens/PastOrders";
import VendorScreen from "./components/screens/VendorPage";
import Register from "./components/screens/Register";
import ChatScreen from "./components/screens/ChatScreen";

const Stack = createStackNavigator();

export default function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const checkLoginState = async () => {
      const storedLoginState = await AsyncStorage.getItem("isLoggedIn");
      setIsLoggedIn(storedLoginState === "true");
    };
    checkLoginState();
  }, []);

  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName={isLoggedIn ? "Taskbar" : "Welcome"}>
        <Stack.Screen name="Welcome" component={WelcomeScreen} />
        <Stack.Screen name="Login" component={LoginScreen} />
        <Stack.Screen name="Taskbar" component={TaskbarNavigator} />
        <Stack.Screen name="EditProfile" component={EditProfileScreen} />
        <Stack.Screen name="PastOrders" component={PastOrdersScreen} />

        <Stack.Screen name="ChatScreen" component={ChatScreen} />
        <Stack.Screen name="VendorPage" component={VendorScreen} />
        <Stack.Screen name="Register" component={Register} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}
