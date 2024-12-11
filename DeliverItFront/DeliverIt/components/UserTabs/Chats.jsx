import React, { useEffect, useState } from "react";
import {
  View,
  Text,
  StyleSheet,
  ActivityIndicator,
  FlatList,
  TouchableOpacity,
} from "react-native";
import AsyncStorage from "@react-native-async-storage/async-storage";
import { useNavigation } from "@react-navigation/native";

const Option2Screen = () => {
  const [userId, setUserId] = useState(null);
  const [conversations, setConversations] = useState([]);
  const [usernames, setUsernames] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigation = useNavigation();

  useEffect(() => {
    const fetchUserId = async () => {
      try {
        const storedUserId = await AsyncStorage.getItem("userId");
        if (storedUserId) {
          setUserId(storedUserId);
        } else {
          setError("User not logged in. Please log in.");
        }
      } catch (err) {
        console.error("Error fetching userId:", err);
        setError("Failed to fetch user information.");
      }
    };
    fetchUserId();
  }, []);

  useEffect(() => {
    if (!userId) return;

    const fetchChatsAndUsers = async () => {
      try {
        // Fetch all chats for the user
        const chatResponse = await fetch(`http://localhost:8080/api/chat/${userId}`);
        if (!chatResponse.ok) {
          throw new Error(`HTTP Error: ${chatResponse.status}`);
        }
        const chatData = await chatResponse.json();

        if (chatData.success && chatData.data) {
          const filteredChats = chatData.data.filter(
            (chat) => chat.userIdOne === userId || chat.userIdTwo === userId
          );
          setConversations(filteredChats);

          // Fetch all users
          const userResponse = await fetch(`http://localhost:8080/api/user/`);
          if (!userResponse.ok) {
            throw new Error(`HTTP Error: ${userResponse.status}`);
          }
          const userData = await userResponse.json();

          if (userData.success && userData.data) {
            const usernamesMap = userData.data.reduce((map, user) => {
              map[user.userId] = user.username;
              return map;
            }, {});
            setUsernames(usernamesMap);
          } else {
            throw new Error("Failed to fetch user information.");
          }
        } else {
          setError("Failed to load chats.");
        }
      } catch (err) {
        console.error("Error fetching data:", err);
        setError(err.message || "An error occurred.");
      } finally {
        setLoading(false);
      }
    };

    fetchChatsAndUsers();
  }, [userId]);

  if (loading) {
    return (
      <View style={styles.loaderContainer}>
        <ActivityIndicator size="large" color="#27AE60" />
        <Text>Loading chats...</Text>
      </View>
    );
  }

  if (error) {
    return (
      <View style={styles.container}>
        <Text style={styles.errorText}>Error: {error}</Text>
      </View>
    );
  }

  if (conversations.length === 0) {
    return (
      <View style={styles.container}>
        <Text>No conversations found.</Text>
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <View style={styles.headerContainer}>
        <View style={styles.textStrokeContainer}>
          <Text style={[styles.header, styles.textStroke]}>Chats</Text>
          <Text style={styles.header}>Chats</Text>
        </View>
      </View>
      <FlatList
        data={conversations}
        keyExtractor={(item) => item.chatId}
        renderItem={({ item }) => {
          const otherUserId = item.userIdOne === userId ? item.userIdTwo : item.userIdOne;
          const username = usernames[otherUserId] || "Unknown User";
          return (
            <TouchableOpacity
              style={styles.chatCard}
              onPress={() => navigation.navigate("ChatScreen", { chatId: item.chatId })}
            >
              <Text style={styles.chatHeader}>Chat with: {username}</Text>
              <Text style={styles.chatDetails}>Click to view messages</Text>
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
    backgroundColor: "#82E0AA",
  },
  loaderContainer: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    backgroundColor: "#82E0AA",
  },
  headerContainer: {
    backgroundColor: "#27AE60",
    paddingVertical: 15,
    alignItems: "center",
    marginBottom: 10,
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
  errorText: {
    color: "red",
    fontSize: 16,
    textAlign: "center",
  },
  chatCard: {
    backgroundColor: "#fff",
    padding: 15,
    marginHorizontal: 15,
    marginVertical: 10,
    borderRadius: 12,
    shadowColor: "#000",
    shadowOffset: { width: 0, height: 3 },
    shadowOpacity: 0.2,
    shadowRadius: 5,
    elevation: 5,
  },
  chatHeader: {
    fontSize: 18,
    fontWeight: "bold",
    color: "#333",
  },
  chatDetails: {
    fontSize: 14,
    color: "#666",
    marginTop: 5,
  },
});

export default Option2Screen;
