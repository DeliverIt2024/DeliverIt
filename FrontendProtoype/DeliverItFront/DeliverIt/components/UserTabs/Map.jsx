import React from "react";
import { StyleSheet, Text, View } from "react-native";

const Option1Screen = () => (
  <View style={styles.container}>
    <Text style={styles.header}>MAPS</Text>
  </View>
);

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    paddingHorizontal: 20,
  },
  header: {
    fontSize: 24,
    fontWeight: "bold",
  },
});

export default Option1Screen;
