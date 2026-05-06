import { useState, useEffect } from 'react';
import {getRacers, toggleBanRacer, toggleUnbanRacer} from '../services/api';

export default function RacersPage() {
    const [racers, setRacers] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState('');

    const [searchQuery, setSearchQuery] = useState('');

    // Runs once when the component mounts
    // Fetches data from the API
    useEffect(() => {

        const fetchData = async () => {
            try {
                const data = await getRacers('');
                setRacers(data);
            } catch (err) {
                setError(err.message);
            } finally {
                setIsLoading(false);
            }
        };

        fetchData();

    }, []);

    // Fetches data from the API based on the search query
    const fetchRacersData = async (query) => {
        setIsLoading(true);
        setError('');
        try {
            const data = await getRacers(query);
            setRacers(data);
        } catch (err) {
            setError(err.message);
        } finally {
            setIsLoading(false);
        }
    };

    // Handles the search form submission
    const handleSearch = (e) => {
        e.preventDefault();
        fetchRacersData(searchQuery);
    };

    // Toggles the ban status of a racer
    const handleToggleBan = async (racerId) => {
        try {
            await toggleBanRacer(racerId);

            setRacers(prevRacers =>
                prevRacers.map(racer =>
                    racer.id === racerId
                        ? { ...racer, isRestricted: !racer.isRestricted }
                        : racer
                )
            );
        } catch (err) {
            alert("Error: " + err.message);
        }
    };

    // Toggles the unban status of a racer
    const handleToggleUnban = async (racerId) => {
        try {
            await toggleUnbanRacer(racerId);

            setRacers(prevRacers =>
                prevRacers.map(racer =>
                    racer.id === racerId
                        ? { ...racer, isRestricted: !racer.isRestricted }
                        : racer
                )
            );
        } catch (err) {
            alert("Error: " + err.message);
        }
    };

    if (isLoading) return <div style={{ padding: '40px', textAlign: 'center' }}>Loading data...</div>;
    if (error) return <div style={{ padding: '40px', color: '#ff4d4d' }}>Error: {error}</div>;

    return (
        <div style={{ padding: '40px 5%', maxWidth: '1000px', margin: '0 auto' }}>
            <h1 style={{ textAlign: 'center', marginBottom: '40px', fontWeight: 'normal' }}>
                Customer administration
            </h1>

            {/* Search form */}
            <form
                onSubmit={handleSearch}
                style={{
                    display: 'flex',
                    gap: '15px',
                    marginBottom: '30px',
                    alignItems: 'center',
                    backgroundColor: '#1e1e1e',
                    padding: '20px',
                    borderRadius: '8px'
                }}
            >
                <label style={{ color: '#ccc', fontWeight: 'bold' }}>Locate customer:</label>

                <input
                    type="text"
                    placeholder="Enter customer name or username."
                    value={searchQuery}
                    onChange={(e) => setSearchQuery(e.target.value)}
                    style={{ flex: 1, padding: '10px', borderRadius: '6px', backgroundColor: '#2a2a2a', color: 'white', border: '1px solid #444' }}
                />

                <button
                    type="submit"
                    style={{ backgroundColor: '#0084ff', color: 'white', padding: '10px 20px', borderRadius: '6px', border: 'none', cursor: 'pointer', fontWeight: 'bold' }}
                >
                    Find
                </button>

                <button
                    type="button"
                    onClick={() => {
                        setSearchQuery('');
                        fetchRacersData('');
                    }}
                    style={{ backgroundColor: '#555', color: 'white', padding: '10px 20px', borderRadius: '6px', border: 'none', cursor: 'pointer', fontWeight: 'bold' }}
                >
                    Reset Filter
                </button>
            </form>

            {/* Informational messages */}
            {isLoading && <div style={{ textAlign: 'center', marginBottom: '20px' }}>Loading data</div>}
            {error && <div style={{ color: '#ff4d4d', textAlign: 'center', marginBottom: '20px' }}>Error: {error}</div>}

            {/* Racers table */}
            <div style={{ overflowX: 'auto' }}>
                <table style={{ width: '100%', borderCollapse: 'collapse', backgroundColor: '#1e1e1e', borderRadius: '8px', overflow: 'hidden' }}>
                    <thead style={{ backgroundColor: '#2a2a2a' }}>
                    <tr>
                        <th style={{ padding: '15px', textAlign: 'left', borderBottom: '1px solid #444' }}>ID</th>
                        <th style={{ padding: '15px', textAlign: 'left', borderBottom: '1px solid #444' }}>Username</th>
                        <th style={{ padding: '15px', textAlign: 'left', borderBottom: '1px solid #444' }}>Name</th>
                        <th style={{ padding: '15px', textAlign: 'left', borderBottom: '1px solid #444' }}>State</th>
                        <th style={{ padding: '15px', textAlign: 'center', borderBottom: '1px solid #444' }}>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {racers.map(racer => (
                        <tr key={racer.id} style={{ borderBottom: '1px solid #333' }}>
                            <td style={{ padding: '15px' }}>{racer.id}</td>
                            <td style={{ padding: '15px' }}>{racer.username}</td>
                            <td style={{ padding: '15px' }}>{racer.name}</td>

                            <td style={{ padding: '15px', color: racer.isRestricted ? '#ff4d4d' : '#4dff4d', fontWeight: 'bold' }}>
                                {racer.isRestricted ? 'Restricted' : 'Active'}
                            </td>

                            <td style={{ padding: '15px', textAlign: 'center' }}>
                                <button
                                    onClick={() => (racer.isRestricted ? handleToggleUnban(racer.id) : handleToggleBan(racer.id))}
                                    style={{
                                        backgroundColor: racer.isRestricted ? '#4dff4d' : '#ff4d4d',
                                        color: '#121212',
                                        border: 'none',
                                        padding: '8px 16px',
                                        borderRadius: '4px',
                                        cursor: 'pointer',
                                        fontWeight: 'bold'
                                    }}
                                >
                                    {racer.isRestricted ? 'Unban' : 'Ban'}
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}