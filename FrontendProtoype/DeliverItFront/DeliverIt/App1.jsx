import React, { useEffect, useState } from "react";
import { NavigationContainer } from "@react-navigation/native";
import { createStackNavigator } from "@react-navigation/stack";
import AsyncStorage from "@react-native-async-storage/async-storage";
import WelcomeScreen from "./components/screens/Welcome";
import LoginScreen from "./components/screens/Login";
import TaskbarNavigator from "./components/Taskbar";

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
      </Stack.Navigator>
    </NavigationContainer>
  );
}
