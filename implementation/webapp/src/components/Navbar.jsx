import Button from './Button';
import { useAuth } from '../context/AuthContext';

export default function Navbar() {
    const { role, logout } = useAuth();

    return (
        <nav style={{
            display: 'flex',
            justifyContent: 'flex-end',
            alignItems: 'center',
            padding: '20px 40px',
            borderBottom: '1px solid #333'
        }}>

            {!role && (
                <>
                    <Button text="Register" to="/register" />
                    <Button text="Login" to="/login" />
                </>
            )}

            {role === 'EMPLOYEE' && (
                <>
                    <Button text="Racers" to="/racers" />
                    <Button text="Race setup" to="/race-setup" />
                </>
            )}

            {role && (
                <button
                    onClick={logout}
                    style={{
                        backgroundColor: '#ff4d4d', color: 'white', border: 'none',
                        padding: '10px 24px', borderRadius: '8px', fontSize: '16px',
                        fontWeight: 'bold', cursor: 'pointer', marginLeft: '15px'
                    }}
                >
                    Logout
                </button>
            )}

        </nav>
    );
}