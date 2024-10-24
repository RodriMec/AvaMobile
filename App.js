import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { HomeScreen } from './componentes/homescreen';
import { NavigationContainer } from '@react-navigation/native';
import { DetailsScreen } from './componentes/detailscreen';
import { MovieScreen } from './componentes/moviescreen';

const Stack = createNativeStackNavigator();
export default function App() {
  return (
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen name="Home" component={HomeScreen} />
        <Stack.Screen name="Details" component={DetailsScreen} />
        <Stack.Screen name="Movies" component={MovieScreen} />
      </Stack.Navigator>
    </NavigationContainer>
  );
}



