import { useState } from 'react';
import { registerRacer } from '../services/api';

export default function RegisterPage() {

    const [formData, setFormData] = useState({
        username: '',
        name: '',
        password: '',
        confirmPassword: '',
        birthday: '',
        city: '',
        address: '',
        phoneNumber: ''
    });

    const [errorMessage, setErrorMessage] = useState('');
    const [usernameError, setUsernameError] = useState(false);
    const [passwordError, setPasswordError] = useState(false);

    /**
     * Handles the change event of the form inputs.
     * @param e - The change event object.
     */
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));

        if (name === 'username') {
            setUsernameError(false);
        }

        if(name === 'password' || name === 'confirmPassword'){
            setPasswordError(false);
        }
    };

    /**
     * Handles the form submission.
     * @param e - The form submission event.
     * @returns {Promise<void>} - A promise that resolves when the form is submitted.
     */
    const handleRegister = async (e) => {
        e.preventDefault();
        setPasswordError(false);
        setUsernameError(false);

        // Validation
        if (formData.password !== formData.confirmPassword) {
            setPasswordError(true);
            return;
        }

        try {
            // Leave out confirmPassword from the data to be sent
            const { confirmPassword, ...dataToSend } = formData;

            // Send cleaned data to the API
            await registerRacer(dataToSend);

            alert("Registration successful!");

            setFormData({
                username: '',
                name: '',
                password: '',
                confirmPassword: '',
                birthday: '',
                city: '',
                address: '',
                phoneNumber: ''
            });

        } catch (error) {
            const errorText = error.message.toLowerCase();
            if (errorText.includes('username') || errorText.includes('use') || errorText.includes('exist')) {
                setUsernameError(true);
            } else {
                setErrorMessage(error.message);
            }
        }
    };

    const inputStyle = {
        width: '100%', padding: '12px', borderRadius: '6px', border: '1px solid #333',
        backgroundColor: '#2a2a2a', color: 'white', fontSize: '16px', boxSizing: 'border-box'
    };

    return (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '80vh', padding: '40px 10px' }}>
            <form
                onSubmit={handleRegister}
                style={{
                    backgroundColor: '#1e1e1e', padding: '40px', borderRadius: '12px',
                    width: '100%', maxWidth: '600px', boxShadow: '0 4px 15px rgba(0, 0, 0, 0.5)'
                }}
            >
                <h2 style={{ textAlign: 'center', marginTop: '0', marginBottom: '30px' }}>
                    New user registration
                </h2>

                {errorMessage && (
                    <div style={{ color: '#ff4d4d', backgroundColor: '#331a1a', padding: '10px', borderRadius: '6px', marginBottom: '20px', textAlign: 'center' }}>
                        {errorMessage}
                    </div>
                )}

                {/* Username and name */}
                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '15px', marginBottom: '15px' }}>
                    <div>
                        <label style={{ display: 'block', marginBottom: '8px', color: '#ccc' }}>Username</label>
                        <input
                            type="text"
                            name="username"
                            value={formData.username}
                            onChange={handleChange}
                            required
                            style={{
                                ...inputStyle,
                                borderColor: usernameError ? '#ff4d4d' : '#333'
                            }}
                        />
                        {usernameError && (
                            <div style={{ color: '#ff4d4d', fontSize: '12px', marginTop: '4px', textAlign: 'right' }}>
                                Already in use
                            </div>
                        )}
                    </div>
                    <div>
                        <label style={{ display: 'block', marginBottom: '8px', color: '#ccc' }}>Full name</label>
                        <input type="text" name="name" value={formData.name} onChange={handleChange} required style={inputStyle} />
                    </div>
                </div>

                {/* Passwords */}
                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '15px', marginBottom: '15px' }}>
                    <div>
                        <label style={{ display: 'block', marginBottom: '8px', color: '#ccc' }}>Password</label>
                        <input
                            type="password"
                            name="password"
                            value={formData.password}
                            onChange={handleChange}
                            required
                            style={{
                                ...inputStyle,
                                borderColor: passwordError ? '#ff4d4d' : '#333'
                            }}
                        />
                        {passwordError && (
                            <div style={{ color: '#ff4d4d', fontSize: '12px', marginTop: '4px', textAlign: 'right' }}>
                                Passwords do not match
                            </div>
                        )}
                    </div>
                    <div>
                        <label style={{ display: 'block', marginBottom: '8px', color: '#ccc' }}>Confirm password</label>
                        <input
                            type="password"
                            name="confirmPassword"
                            value={formData.confirmPassword}
                            onChange={handleChange}
                            required
                            style={{
                                ...inputStyle,
                                borderColor: passwordError ? '#ff4d4d' : '#333'
                            }}
                        />
                    </div>
                </div>

                {/* Birthday and phone number */}
                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '15px', marginBottom: '15px' }}>
                    <div>
                        <label style={{ display: 'block', marginBottom: '8px', color: '#ccc' }}>Date of birth</label>
                        <input type="date" name="birthday" value={formData.birthday} onChange={handleChange} required style={inputStyle} />
                    </div>
                    <div>
                        <label style={{ display: 'block', marginBottom: '8px', color: '#ccc' }}>Phone number</label>
                        <input type="tel" name="phoneNumber" value={formData.phoneNumber} onChange={handleChange} required style={inputStyle} />
                    </div>
                </div>

                {/* City and address */}
                <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '15px', marginBottom: '30px' }}>
                    <div>
                        <label style={{ display: 'block', marginBottom: '8px', color: '#ccc' }}>City</label>
                        <input type="text" name="city" value={formData.city} onChange={handleChange} required style={inputStyle} />
                    </div>
                    <div>
                        <label style={{ display: 'block', marginBottom: '8px', color: '#ccc' }}>Address</label>
                        <input type="text" name="address" value={formData.address} onChange={handleChange} required style={inputStyle} />
                    </div>
                </div>

                <button
                    type="submit"
                    style={{ width: '100%', backgroundColor: '#0084ff', color: 'white', border: 'none', padding: '14px', borderRadius: '6px', fontSize: '16px', fontWeight: 'bold', cursor: 'pointer' }}
                >
                    Register
                </button>
            </form>
        </div>
    );
}