import { Button, Text, View } from "react-native";

export function DetailsScreen({ navigation }) {
    return (
        <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
            <Text>HOME SCREEN</Text>
            <Button
                title="Home"
                onPress={() => navigation.goBack("Home")}
            ></Button>
        </View>
    );
}