import { useState } from 'react';
import {createRace} from "../services/api.js";

export default function RaceSetupPage() {

    const today = new Date();
    const currentDate = today.toISOString().split('T')[0];

    const [timeError, setTimeError] = useState(false);
    const [trackError, setTrackError] = useState(false);

    const [formData, setFormData] = useState({
        raceDate: currentDate,
        raceTime: '',
        track: 'track_1'
    });

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

        if (name === 'raceTime') {
            setTimeError(false);
        }
        if (name === 'track') {
            setTrackError(false);
        }
    };

    /**
     * Handles the form submission.
     * @param e - The form submission event.
     * @returns {Promise<void>} - A promise that resolves when the form is submitted.
     */
    const handleSubmit = async (e) => {
        e.preventDefault();
        setTimeError(false);
        setTrackError(false);

        try {
            const msg = await createRace(formData);

            alert(msg || 'Race setup successful!');

            setFormData(prev => ({
                ...prev,
                raceTime: ''
            }));

        } catch (e) {
            alert(e.message);
            if(e.message.includes('past')) setTimeError(true);
            if(e.message.includes('track')) setTrackError(true);
        }
    };

    const inputStyle = {
        width: '100%', padding: '12px', borderRadius: '6px', border: '1px solid #333',
        backgroundColor: '#2a2a2a', color: 'white', fontSize: '16px', boxSizing: 'border-box'
    };

    return (
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', minHeight: '80vh', padding: '40px 10px' }}>
            <form
                onSubmit={handleSubmit}
                style={{
                    backgroundColor: '#1e1e1e', padding: '40px', borderRadius: '12px',
                    width: '100%', maxWidth: '500px', boxShadow: '0 4px 15px rgba(0, 0, 0, 0.5)'
                }}
            >
                <h2 style={{ textAlign: 'center', marginTop: '0', marginBottom: '30px', fontWeight: 'normal' }}>
                    New race setup
                </h2>

                <div style={{ marginBottom: '20px' }}>
                    <label style={{ display: 'block', marginBottom: '8px', color: '#ccc' }}>Date</label>
                    <input
                        type="date"
                        name="raceDate"
                        value={formData.raceDate}
                        onChange={handleChange}
                        required
                        style={inputStyle}
                    />
                </div>


                <div style={{ marginBottom: '20px' }}>
                    <label style={{ display: 'block', marginBottom: '8px', color: '#ccc' }}>Start time</label>
                    <input
                        type="time"
                        name="raceTime"
                        value={formData.raceTime}
                        onChange={handleChange}
                        required
                        style={{
                            ...inputStyle,
                            borderColor: timeError ? '#ff4d4d' : '#333'
                        }}
                    />
                </div>

                {timeError && (
                    <div style={{ color: '#ff4d4d', fontSize: '12px', marginTop: '4px', textAlign: 'right' }}>
                        Invalid
                    </div>
                )}

                <div style={{ marginBottom: '30px' }}>
                    <label style={{ display: 'block', marginBottom: '8px', color: '#ccc' }}>Race track</label>
                    <select
                        name="track"
                        value={formData.track}
                        onChange={handleChange}
                        required
                        style={{
                            ...inputStyle,
                            borderColor: trackError ? '#ff4d4d' : '#333'
                        }}
                    >
                        <option value="track_1">Track 1 (Indoor - 500m)</option>
                        <option value="track_2">Track 2 (Outdoor - 1200m)</option>
                    </select>
                    {trackError && (
                        <div style={{ color: '#ff4d4d', fontSize: '12px', marginTop: '4px', textAlign: 'right' }}>
                            Invalid
                        </div>
                    )}
                </div>



                <button
                    type="submit"
                    style={{ width: '100%', backgroundColor: '#0084ff', color: 'white', border: 'none', padding: '14px', borderRadius: '6px', fontSize: '16px', fontWeight: 'bold', cursor: 'pointer' }}
                >
                    Confirm
                </button>
            </form>
        </div>
    );
}