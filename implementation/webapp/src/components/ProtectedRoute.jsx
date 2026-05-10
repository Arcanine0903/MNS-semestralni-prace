import { Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

/**
 * ProtectedRoute component
 * @param children - children components
 * @param allowedRoles - allowed roles
 * @returns {*|React.JSX.Element} - children components if user is authenticated, otherwise redirects to login page
 * @constructor
 */
export default function ProtectedRoute({ children, allowedRoles }) {
    const { role } = useAuth();

    if (!role) {
        return <Navigate to="/login" replace />;
    }

    if (allowedRoles && !allowedRoles.includes(role)) {
        return <Navigate to="/" replace />;
    }

    return children;
}