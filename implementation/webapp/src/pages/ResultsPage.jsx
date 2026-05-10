import { useState, useEffect } from 'react';
import { getRaces, getRaceResults } from '../services/api';

export default function ResultsPage() {
    const [races, setRaces] = useState([]);
    const [selectedRaceId, setSelectedRaceId] = useState('');
    const [results, setResults] = useState([]);

    const [isLoadingRaces, setIsLoadingRaces] = useState(true);
    const [isLoadingResults, setIsLoadingResults] = useState(false);
    const [error, setError] = useState('');

    /**
     * Ran during the first render of the component.
     * Loads and prepares data for the default race.
     */
    useEffect(() => {
        const fetchRaces = async () => {
            try {
                const data = await getRaces();
                const sortedRaces = data.sort((a, b) => b.id - a.id);
                setRaces(sortedRaces);

                if (sortedRaces.length > 0) {
                    setSelectedRaceId(sortedRaces[0].id);
                }
            } catch (err) {
                setError(err.message);
            } finally {
                setIsLoadingRaces(false);
            }
        };

        fetchRaces();
    }, []);

    /**
     * Ran when the selected race changes.
     */
    useEffect(() => {
        if (!selectedRaceId) return;

        const fetchInitialResults = async () => {
            setIsLoadingResults(true);
            setError('');
            try {
                const data = await getRaceResults(selectedRaceId);
                const sortedResults = data.sort((a, b) => a.bestTimeMs - b.bestTimeMs);
                setResults(sortedResults);
            } catch (err) {
                setError(err.message);
                setResults([]);
            } finally {
                setIsLoadingResults(false);
            }
        };

        fetchInitialResults();

        // Open an SSE connection to the server
        const eventSource = new EventSource(`http://localhost:8080/api/races/${selectedRaceId}/live`);

        // Listening for new results
        eventSource.addEventListener("new-result", (event) => {
            const incomingResult = JSON.parse(event.data);

            // Update the leaderboard with the new result
            setResults(prevResults => {
                const exists = prevResults.some(r => r.id === incomingResult.id);
                if (exists) return prevResults;

                const newResultsList = [...prevResults, incomingResult];
                return newResultsList.sort((a, b) => a.bestTimeMs - b.bestTimeMs);
            });
        });

        eventSource.onerror = () => {
            console.error("SSE connection lost.");
            eventSource.close();
        };

        // Cleanup function to close the SSE connection when the component unmounts
        return () => {
            eventSource.close();
        };

    }, [selectedRaceId]);

    /**
     * Formats the time in milliseconds to a string in the format "mm:ss.sss".
     * @param ms {number} - The time in milliseconds.
     * @returns {string} - The formatted time string.
     */
    const formatTime = (ms) => {
        if (!ms) return '-';
        const minutes = Math.floor(ms / 60000);
        const seconds = Math.floor((ms % 60000) / 1000);
        const milliseconds = ms % 1000;
        return `${minutes}:${seconds.toString().padStart(2, '0')}.${milliseconds.toString().padStart(3, '0')}`;
    };

    return (
        <div style={{ padding: '40px 5%', maxWidth: '900px', margin: '0 auto' }}>
            <h1 style={{ textAlign: 'center', marginBottom: '30px', fontWeight: 'normal' }}>
                Race results (Live)
            </h1>

            {error && (
                <div style={{ color: '#ff4d4d', backgroundColor: '#331a1a', padding: '10px', borderRadius: '6px', marginBottom: '20px', textAlign: 'center' }}>
                    Error: {error}
                </div>
            )}

            <div style={{ backgroundColor: '#1e1e1e', padding: '20px', borderRadius: '8px', marginBottom: '30px' }}>
                <label style={{ display: 'block', marginBottom: '8px', color: '#ccc', fontWeight: 'bold' }}>
                    Chose a race to track:
                </label>
                {!isLoadingRaces && (
                    <select
                        value={selectedRaceId}
                        onChange={(e) => setSelectedRaceId(e.target.value)}
                        style={{
                            width: '100%', padding: '12px', borderRadius: '6px', border: '1px solid #333',
                            backgroundColor: '#2a2a2a', color: 'white', fontSize: '16px'
                        }}
                    >
                        {races.length === 0 ? (
                            <option value="">No races in the database.</option>
                        ) : (
                            races.map(race => (
                                <option key={race.id} value={race.id}>
                                    Race #{race.id} {race.date ? `- ${race.date}` : ''}
                                </option>
                            ))
                        )}
                    </select>
                )}
            </div>

            {isLoadingResults ? (
                <div style={{ textAlign: 'center', color: '#ccc' }}>Loading results..</div>
            ) : (
                selectedRaceId && (
                    <div style={{ overflowX: 'auto' }}>
                        <table style={{ width: '100%', borderCollapse: 'collapse', backgroundColor: '#1e1e1e', borderRadius: '8px', overflow: 'hidden' }}>
                            <thead style={{ backgroundColor: '#2a2a2a' }}>
                            <tr>
                                <th style={{ padding: '15px', textAlign: 'center', borderBottom: '1px solid #444', width: '80px' }}>Place</th>
                                <th style={{ padding: '15px', textAlign: 'left', borderBottom: '1px solid #444' }}>Racer</th>
                                <th style={{ padding: '15px', textAlign: 'center', borderBottom: '1px solid #444' }}>Kart</th>
                                <th style={{ padding: '15px', textAlign: 'right', borderBottom: '1px solid #444' }}>Best time</th>
                            </tr>
                            </thead>
                            <tbody>
                            {results.length === 0 ? (
                                <tr>
                                    <td colSpan="4" style={{ padding: '20px', textAlign: 'center', color: '#ccc' }}>
                                        No results for this race so far.
                                    </td>
                                </tr>
                            ) : (
                                results.map((result, index) => (
                                    <tr key={index} style={{ borderBottom: '1px solid #333', transition: 'all 0.5s ease' }}>
                                        <td style={{ padding: '15px', textAlign: 'center', fontWeight: 'bold', color: index === 0 ? '#FFD700' : index === 1 ? '#C0C0C0' : index === 2 ? '#CD7F32' : 'white' }}>
                                            {index + 1}.
                                        </td>
                                        <td style={{ padding: '15px' }}>{result.username}</td>
                                        <td style={{ padding: '15px', textAlign: 'center' }}>Kart {result.kartNumber}</td>
                                        <td style={{ padding: '15px', textAlign: 'right', fontWeight: 'bold', color: '#4dff4d' }}>
                                            {formatTime(result.bestTimeMs)}
                                        </td>
                                    </tr>
                                ))
                            )}
                            </tbody>
                        </table>
                    </div>
                )
            )}
        </div>
    );
}