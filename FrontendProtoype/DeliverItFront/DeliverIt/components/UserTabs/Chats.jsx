import React from "react";
import { StyleSheet, Text, View } from "react-native";

const Option2Screen = () => (
  <View style={styles.container}>
    <Text style={styles.header}>CHATS</Text>
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

export default Option2Screen;
