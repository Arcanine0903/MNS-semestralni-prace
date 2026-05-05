import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { loginUser } from '../services/api';
import { useAuth } from '../context/AuthContext';

export default function LoginPage() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const [errorMessage, setErrorMessage] = useState('');

    const { login } = useAuth();
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();
        setErrorMessage('');

        try {
            const result = await loginUser(username, password);

            login(result);

            navigate('/');
        } catch (error) {
            setErrorMessage(error.message);
        }
    };

    return (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '80vh' }}>
            <form
                onSubmit={handleLogin}
                style={{
                    backgroundColor: '#1e1e1e', padding: '40px', borderRadius: '12px',
                    width: '100%', maxWidth: '400px', boxShadow: '0 4px 15px rgba(0, 0, 0, 0.5)'
                }}
            >
                <h2 style={{ textAlign: 'center', marginTop: '0', marginBottom: '30px' }}>
                    Login to Kart Arena
                </h2>

                {errorMessage && (
                    <div style={{ color: '#ff4d4d', backgroundColor: '#331a1a', padding: '10px', borderRadius: '6px', marginBottom: '20px', textAlign: 'center' }}>
                        {errorMessage}
                    </div>
                )}

                <div style={{ marginBottom: '20px' }}>
                    <label style={{ display: 'block', marginBottom: '8px', color: '#ccc' }}>Username</label>
                    <input
                        type="text" value={username} onChange={(e) => setUsername(e.target.value)} required
                        style={{ width: '100%', padding: '12px', borderRadius: '6px', border: '1px solid #333', backgroundColor: '#2a2a2a', color: 'white', fontSize: '16px' }}
                    />
                </div>

                <div style={{ marginBottom: '30px' }}>
                    <label style={{ display: 'block', marginBottom: '8px', color: '#ccc' }}>Password</label>
                    <input
                        type="password" value={password} onChange={(e) => setPassword(e.target.value)} required
                        style={{ width: '100%', padding: '12px', borderRadius: '6px', border: '1px solid #333', backgroundColor: '#2a2a2a', color: 'white', fontSize: '16px' }}
                    />
                </div>

                <button
                    type="submit"
                    style={{ width: '100%', backgroundColor: '#0084ff', color: 'white', border: 'none', padding: '14px', borderRadius: '6px', fontSize: '16px', fontWeight: 'bold', cursor: 'pointer' }}
                >
                    Sign In
                </button>
            </form>
        </div>
    );
}