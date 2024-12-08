
import React, { useState, useEffect } from "react";
import { StyleSheet, Text, TextInput, Button, View, Alert } from "react-native";
import axios from "axios";
import { NavigationContainer } from "@react-navigation/native";
import { createStackNavigator } from "@react-navigation/stack";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import Icon from "react-native-vector-icons/MaterialIcons"; // for tab icons
import AsyncStorage from "@react-native-async-storage/async-storage"; // Import AsyncStorage

const Stack = createStackNavigator();
const Tab = createBottomTabNavigator();
/*
// Welcome Screen
function WelcomeScreen({ navigation }) {
  return (
    <View style={styles.welcomeContainer}>
      <Text style={styles.welcomeText}>Welcome to DeliverIt!</Text>
      <View style={styles.buttonContainer}>
        <Button title="Login" onPress={() => navigation.navigate("Login")} color="#27AE60" />
        <Button title="Register" onPress={() => navigation.navigate("Login")} color="#F7DC6F" />
      </View>
    </View>
  );
}
*/
/*
// Login Screen
function LoginScreen({ navigation }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async () => {
    try {
      const response = await axios.post("http://localhost:8080/api/user/login", {
        email,
        password,
      });

      if (response.data.success) {
        // Save the login state using AsyncStorage
        await AsyncStorage.setItem("isLoggedIn", "true");
        Alert.alert("Success", "Login successful!");
        navigation.navigate("Taskbar"); // Navigate to Taskbar after successful login
      } else {
        Alert.alert("Error", response.data.message || "Login failed!");
      }
    } catch (error) {
      console.error(error);
      Alert.alert("Error", "Something went wrong!");
    }
  };

  return (
    <View style={styles.container}>
      <Text style={styles.header}>Login</Text>
      <TextInput
        style={styles.input}
        placeholder="Email"
        value={email}
        onChangeText={setEmail}
        keyboardType="email-address"
      />
      <TextInput
        style={styles.input}
        placeholder="Password"
        value={password}
        onChangeText={setPassword}
        secureTextEntry
      />
      <View style={styles.buttonContainer}>
        <Button title="Login" onPress={handleLogin} color="#27AE60" />
      </View>
    </View>
  );
}

// Dashboard Screen with Taskbar
function DashboardScreen() {
  return (
    <View style={styles.container}>
      <Text style={styles.header}>Dashboard</Text>
      <Text style={styles.header}>Welcome to the Dashboard!</Text>
    </View>
  );
}

// Example screens for each tab
function Option1Screen() {
  return (
    <View style={styles.container}>
      <Text style={styles.header}>Option 1</Text>
    </View>
  );
}

function Option2Screen() {
  return (
    <View style={styles.container}>
      <Text style={styles.header}>Option 2</Text>
    </View>
  );
}

function Option3Screen() {
  return (
    <View style={styles.container}>
      <Text style={styles.header}>Option 3</Text>
    </View>
  );
}

function Option4Screen() {
  return (
    <View style={styles.container}>
      <Text style={styles.header}>Option 4</Text>
    </View>
  );
}

function Option5Screen() {
  return (
    <View style={styles.container}>
      <Text style={styles.header}>Option 5</Text>
    </View>
  );
}

// Bottom Tab Navigator for Taskbar
function TaskbarNavigator() {
  return (
    <Tab.Navigator>
      <Tab.Screen
        name="Option 1"
        component={Option1Screen}
        options={{
          tabBarIcon: () => <Icon name="map" size={25} color="#fff" />,
        }}
      />
      <Tab.Screen
        name="Option 2"
        component={Option2Screen}
        options={{
          tabBarIcon: () => <Icon name="notifications" size={25} color="#fff" />,
        }}
      />
      <Tab.Screen
        name="Option 3"
        component={Option3Screen}
        options={{
          tabBarIcon: () => <Icon name="search" size={25} color="#fff" />,
        }}
      />
      <Tab.Screen
        name="Option 4"
        component={Option4Screen}
        options={{
          tabBarIcon: () => <Icon name="home" size={25} color="#fff" />,
        }}
      />
      <Tab.Screen
        name="Option 5"
        component={Option5Screen}
        options={{
          tabBarIcon: () => <Icon name="account-circle" size={25} color="#fff" />,
        }}
      />
    </Tab.Navigator>
  );
}

export default function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  // Check if the user is logged in when the app is loaded
  useEffect(() => {
    const checkLoginState = async () => {
      const storedLoginState = await AsyncStorage.getItem("isLoggedIn");
      if (storedLoginState === "true") {
        setIsLoggedIn(true);
      }
    };
    checkLoginState();
  }, []);

  return (
    <NavigationContainer>
      <Stack.Navigator initialRouteName={isLoggedIn ? "Taskbar" : "Welcome"}>
        <Stack.Screen name="Welcome" component={WelcomeScreen} />
        <Stack.Screen name="Login" component={LoginScreen} />
        <Stack.Screen name="Dashboard" component={DashboardScreen} />
        <Stack.Screen name="Taskbar" component={TaskbarNavigator} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    paddingHorizontal: 20,
    backgroundColor: "#f5f5f5",
  },
  welcomeContainer: {
    flex: 1,
    justifyContent: "center",
    paddingHorizontal: 20,
    backgroundColor: "#82E0AA", // Soft green background
  },
  welcomeText: {
    fontSize: 30,
    fontWeight: "bold",
    color: "#F7DC6F", // Yellow text
    textAlign: "center",
    marginBottom: 20,
  },
  header: {
    fontSize: 24,
    fontWeight: "bold",
    marginBottom: 20,
    textAlign: "center",
    color: "#27AE60", // Green header
  },
  input: {
    height: 40,
    borderColor: "#27AE60", // Green border
    borderWidth: 1,
    marginBottom: 10,
    paddingHorizontal: 10,
    borderRadius: 5,
    backgroundColor: "#fff",
  },
  buttonContainer: {
    marginTop: 20,
    width: "100%",
    backgroundColor: "#fff", // Make sure the button container has a white background to stand out
  },
});
*/