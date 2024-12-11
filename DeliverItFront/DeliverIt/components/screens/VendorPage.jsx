import React, { useState, useEffect } from "react";
import { StyleSheet, Text, View, ActivityIndicator, Image, FlatList, ScrollView } from "react-native";
import axios from "axios";
import Icon from 'react-native-vector-icons/FontAwesome';

const VendorScreen = ({ route }) => {
  const { vendorId } = route.params;
  const [vendor, setVendor] = useState(null);
  const [loading, setLoading] = useState(true);
  const [menuItems, setMenuItems] = useState([]);
  const [menuLoading, setMenuLoading] = useState(false);

  useEffect(() => {
    const fetchVendorDetails = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/vendor/${vendorId}`);
        setVendor(response.data.data);
        fetchMenuItems(response.data.data.menu);
      } catch (error) {
        console.error("Failed to fetch vendor details:", error);
      } finally {
        setLoading(false);
      }
    };

    const fetchMenuItems = async (menuIds) => {
      setMenuLoading(true);
      try {
        const itemResponses = await Promise.all(
          menuIds.map((itemId) => axios.get(`http://localhost:8080/api/item/${itemId}`))
        );
        const items = itemResponses.map((res) => res.data);
        setMenuItems(items);
      } catch (error) {
        console.error("Failed to fetch menu items:", error);
      } finally {
        setMenuLoading(false);
      }
    };

    fetchVendorDetails();
  }, [vendorId]);

  const renderRating = (averageRating) => {
    const fullStars = Math.floor(averageRating);
    const halfStar = averageRating % 1 !== 0;

    const stars = [];
    for (let i = 0; i < fullStars; i++) {
      stars.push(<Icon key={`star-${i}`} name="star" size={24} color="#FFD700" />);
    }
    if (halfStar) {
      stars.push(<Icon key="half-star" name="star-half-o" size={24} color="#FFD700" />);
    }
    while (stars.length < 5) {
      stars.push(<Icon key={`empty-star-${stars.length}`} name="star-o" size={24} color="#FFD700" />);
    }

    return <View style={styles.starsContainer}>{stars}</View>;
  };

  if (loading) {
    return (
      <View style={styles.loaderContainer}>
        <ActivityIndicator size="large" color="#27AE60" />
      </View>
    );
  }

  if (!vendor) {
    return (
      <View style={styles.container}>
        <Text>No vendor details available.</Text>
      </View>
    );
  }

  const { address, averageRating } = vendor;
  const addressText = address ? `${address.street}, ${address.city}, ${address.state}, ${address.zip}` : "Address not available";

  return (
    <ScrollView contentContainerStyle={{ flexGrow: 1 }}>
      <View style={styles.headerContainer}>
        <View style={styles.textStrokeContainer}>
          <Text style={[styles.header, styles.textStroke]}>{vendor.name}</Text>
          <Text style={styles.header}>{vendor.name}</Text>
        </View>
        <Image
          source={{ uri: vendor.imageUrl || "https://via.placeholder.com/150" }}
          style={styles.vendorImage}
        />
      </View>
  
      {/* Details Section */}
      <View style={styles.detailsContainer}>
        <View style={styles.detailRow}>
          <Icon name="envelope" size={20} color="#27AE60" />
          <Text style={styles.detailText}> {vendor.email}</Text>
        </View>
        <View style={styles.detailRow}>
          <Icon name="phone" size={20} color="#27AE60" />
          <Text style={styles.detailText}> {vendor.phone}</Text>
        </View>
        <View style={styles.detailRow}>
          <Icon name="map-marker" size={20} color="#27AE60" />
          <Text style={styles.detailText}> {addressText}</Text>
        </View>
        <View style={styles.ratingContainer}>
          <Text style={styles.ratingText}>Rating: </Text>
          {renderRating(averageRating)}
        </View>
      </View>
  
      {/* Menu Section */}
      <View style={styles.menuContainer}>
        <Text style={styles.menuHeader}>Menu</Text>
        {menuLoading ? (
          <ActivityIndicator size="large" color="#27AE60" />
        ) : (
          <FlatList
            data={menuItems}
            keyExtractor={(item) => item.id}
            renderItem={({ item }) => (
              <View style={styles.card}>
                <Text style={styles.menuItemTitle}>{item.data.name}</Text>
                <Text style={styles.menuItemDescription}>{item.data.description}</Text>
                <Text style={styles.menuItemPrice}>${item.data.price}</Text>
              </View>
            )}
          />
        )}
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#f9f9f9",
  },
  loaderContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#f5f5f5",
  },
  headerContainer: {
    backgroundColor: "#27AE60",
    padding: 20,
    alignItems: "center",
    borderBottomLeftRadius: 20,
    borderBottomRightRadius: 20,
  },
  header: {
    fontSize: 28,
    color: "#F7DC6F",
    fontWeight: "bold",
  },
  vendorImage: {
    width: 120,
    height: 120,
    borderRadius: 60,
    marginTop: 10,
    borderWidth: 3,
    borderColor: "#fff",
  },
  detailsContainer: {
    padding: 20,
    margin: 20,
    backgroundColor: "#ffffff",
    borderRadius: 12,
    elevation: 6,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.1,
    shadowRadius: 6,
    alignItems: "center",
  },
  detailRow: {
    flexDirection: "row",
    alignItems: "center",
    marginVertical: 5,
  },
  detailText: {
    fontSize: 16,
    marginLeft: 10,
    color: "#333",
  },
  ratingContainer: {
    flexDirection: "row",
    alignItems: "center",
    marginTop: 10,
  },
  ratingText: {
    fontSize: 16,
    color: "#333",
  },
  starsContainer: {
    flexDirection: "row",
    marginLeft: 5,
  },
  menuContainer: {
    padding: 20,
  },
  menuHeader: {
    fontSize: 22,
    fontWeight: "bold",
    color: "#27AE60",
    marginBottom: 10,
    textShadowColor: "#000",
    textShadowOffset: { width: -1, height: 1 },
    textShadowRadius: 1,
  },
  card: {
    backgroundColor: "#ffffff",
    borderRadius: 12,
    padding: 15,
    marginBottom: 15,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.1,
    shadowRadius: 6,
    elevation: 4,
  },
  menuItemTitle: {
    fontSize: 18,
    fontWeight: "bold",
    color: "#27AE60",
    marginBottom: 5,
    textShadowColor: "#000",
    textShadowOffset: { width: -0.75, height: 0.75 },
    textShadowRadius: 1,
  },
  menuItemDescription: {
    fontSize: 14,
    color: "#666",
    marginBottom: 10,
  },
  menuItemPrice: {
    fontSize: 16,
    fontWeight: "bold",
    color: "#333",
  },
  textStroke: {
    position: "absolute",
    color: "transparent",
    textShadowColor: "#000",
    textShadowOffset: { width: -1, height: 1 },
    textShadowRadius: 1,
  },
  textStrokeContainer: {
    position: "relative",
    alignItems: "center",
  },
});

export default VendorScreen;
