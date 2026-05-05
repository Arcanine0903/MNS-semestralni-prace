import { createContext, useContext, useState } from 'react';

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [role, setRole] = useState(() => {
        return localStorage.getItem('userRole') || null;
    });


    const login = (newRole) => {
        setRole(newRole);
        localStorage.setItem('userRole', newRole);
    };

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

export const useAuth = () => {
    return useContext(AuthContext);
};