import React, { useState, useEffect } from "react";
import {
  StyleSheet,
  Text,
  View,
  FlatList,
  ActivityIndicator,
  TouchableOpacity,
  Image,
} from "react-native";
import axios from "axios";
import { useNavigation } from "@react-navigation/native";

const Option4Screen = () => {
  const [vendors, setVendors] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigation = useNavigation();

  useEffect(() => {
    const fetchVendors = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/vendor/");
        setVendors(response.data.data); 
      } catch (error) {
        console.error("Failed to fetch vendors:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchVendors();
  }, []);

  if (loading) {
    return (
      <View style={styles.loaderContainer}>
        <ActivityIndicator size="large" color="#27AE60" />
      </View>
    );
  }

  if (vendors.length === 0) {
    return (
      <View style={styles.container}>
        <Text>No vendors available.</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <View style={styles.headerContainer}>
        <View style={styles.textStrokeContainer}>
          <Text style={[styles.header, styles.textStroke]}>Vendors</Text>
          <Text style={styles.header}>Vendors</Text>
        </View>
      </View>
      <FlatList
        data={vendors}
        keyExtractor={(item) =>
          item.vendorId ? item.vendorId.toString() : item.id.toString()
        }
        renderItem={({ item }) => {
          return (
            <TouchableOpacity
              style={styles.vendorCard}
              onPress={() =>
                navigation.navigate("VendorPage", { vendorId: item.vendorId })
              }
            >
              <Image
                source={{
                  uri: item.imageUrl || "https://via.placeholder.com/80",
                }}
                style={styles.vendorImage}
              />
              <View style={styles.vendorInfo}>
                <Text style={styles.vendorName}>
                  {item.name || "Name not available"}
                </Text>
                <Text>{item.email || "Email not available"}</Text>
                <Text>{item.phone || "Phone not available"}</Text>
              </View>
            </TouchableOpacity>
          );
        }}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    backgroundColor: "#82E0AA",
  },
  loaderContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#82E0AA",
  },
  headerContainer: {
    backgroundColor: "#27AE60", // Green
    paddingVertical: 15,       
    alignItems: "center",      
    marginBottom: 0,          
  },
  header: {
    fontSize: 24,
    fontWeight: "bold",
    color: "#F7DC6F",         
    textAlign: "center",
    width: "100%",
  },
  textStrokeContainer: {
    position: "relative",
    alignItems: "center",
  },
  textStroke: {
    position: "absolute",
    color: "transparent",
    textShadowColor: "#000", 
    textShadowOffset: { width: -1, height: 1 }, 
    textShadowRadius: 1,      
  },
  vendorCard: {
    flexDirection: "row",
    backgroundColor: "#fff",
    padding: 15,
    marginBottom: 10,
    borderRadius: 12,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 3 },
    shadowOpacity: 0.4,
    shadowRadius: 6,
    elevation: 8,
    alignItems: "center",
  },
  vendorImage: {
    width: 80,
    height: 80,
    borderRadius: 40,
    backgroundColor: "#ccc",
    marginRight: 15,
  },
  vendorInfo: {
    flex: 1,
  },
  vendorName: {
    fontSize: 18,
    fontWeight: "bold",
    marginBottom: 5,
  },
});

export default Option4Screen;
