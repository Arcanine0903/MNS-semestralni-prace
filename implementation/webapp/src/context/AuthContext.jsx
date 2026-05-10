import { createContext, useContext, useState } from 'react';

// Create a context for authentication
const AuthContext = createContext();

/**
 * AuthProvider component
 * @param children - children components
 * @returns {React.JSX.Element} - children components wrapped in AuthContext.Provider
 * @constructor
 */
export const AuthProvider = ({ children }) => {
    const [role, setRole] = useState(() => {
        return localStorage.getItem('userRole') || null;
    });

    /**
     * Login function to set the user role and store it in localStorage
     * @param newRole - new role to set
     */
    const login = (newRole) => {
        setRole(newRole);
        localStorage.setItem('userRole', newRole);
    };

    /**
     * Logout function to remove the user role and clear localStorage
     */
    const logout = () => {
        setRole(null);
        localStorage.removeItem('userRole');
    };

    return (
        <AuthContext.Provider value={{ role, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

// eslint-disable-next-line react-refresh/only-export-components
export const useAuth = () => {
    return useContext(AuthContext);
};