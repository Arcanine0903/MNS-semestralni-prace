import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import HomePage from './pages/HomePage';
import LoginPage from './pages/LoginPage';
import RacersPage from './pages/RacersPage';
import RegisterPage from './pages/RegisterPage';
import ProtectedRoute from './components/ProtectedRoute';
import { AuthProvider } from './context/AuthContext';
import RaceSetupPage from "./pages/RaceSetupPage.jsx";
import ResultsPage from "./pages/ResultsPage.jsx";

function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <Navbar />
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/login" element={<LoginPage />} />
                    <Route path="/register" element={<RegisterPage />} />

                    <Route
                        path="/racers"
                        element={
                            <ProtectedRoute allowedRoles={['EMPLOYEE']}>
                                <RacersPage/>
                            </ProtectedRoute>
                        }
                    />

                    <Route
                        path="/race-setup"
                        element={
                            <ProtectedRoute allowedRoles={['EMPLOYEE']}>
                                <RaceSetupPage/>
                            </ProtectedRoute>
                        }
                    />

                    <Route
                        path="/results"
                        element={
                            <ProtectedRoute allowedRoles={['EMPLOYEE']}>
                                <ResultsPage/>
                            </ProtectedRoute>
                        }
                    />
                </Routes>
            </BrowserRouter>
        </AuthProvider>
    );
}

export default App;