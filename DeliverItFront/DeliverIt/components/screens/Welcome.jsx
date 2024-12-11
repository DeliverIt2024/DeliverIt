import React from "react";
import { StyleSheet, Text, TouchableOpacity, View } from "react-native";

const WelcomeScreen = ({ navigation }) => {
  return (
    <View style={styles.welcomeContainer}>
      <View style={styles.textWrapper}>
        <Text style={[styles.welcomeText, styles.textStroke]}>Welcome to DeliverIt!</Text>
        <Text style={styles.welcomeText}>Welcome to DeliverIt!</Text>
      </View>
      <View style={styles.buttonContainer}>
        <TouchableOpacity
          style={[styles.button, styles.loginButton]}
          onPress={() => navigation.navigate("Login")}
        >
          <View style={styles.buttonTextWrapper}>
            <Text style={[styles.buttonText, styles.textStroke]}>Login</Text>
            <Text style={styles.buttonText}>Login</Text>
          </View>
        </TouchableOpacity>
        <TouchableOpacity
          style={[styles.button, styles.registerButton]}
          onPress={() => navigation.navigate("Register")}
        >
          <View style={styles.buttonTextWrapper}>
            <Text style={[styles.buttonText, styles.textStroke]}>Register</Text>
            <Text style={styles.buttonText}>Register</Text>
          </View>
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  welcomeContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    paddingHorizontal: 20,
    backgroundColor: "#82E0AA",
  },
  welcomeText: {
    fontSize: 30,
    fontWeight: "bold",
    color: "#F7DC6F",
    textAlign: "center",
    marginBottom: 40,
  },
  textStroke: {
    position: "absolute",
    left: 0,
    right: 0,
    color: "black", 
    textShadowColor: "black",
    textShadowOffset: { width: -1, height: 1 },
    textShadowRadius: 1,
  },
  buttonContainer: {
    width: "100%",
    alignItems: "center",
  },
  button: {
    width: 250, 
    height: 60, 
    justifyContent: "center",
    alignItems: "center",
    borderRadius: 8, 
    marginBottom: 30, 
  },
  loginButton: {
    backgroundColor: "#27AE60",
  },
  registerButton: {
    backgroundColor: "#F7DC6F", 
  },
  buttonText: {
    fontSize: 30, 
    fontWeight: "bold",
    color: "#fff", 
  },
});

export default WelcomeScreen;
