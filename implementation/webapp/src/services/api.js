const BASE_URL = 'http://localhost:8080/api';

/**
 * Send a POST request to the server to log in a user
 * @param username - username of the user
 * @param password - password of the user
 * @returns {Promise<string>} - role of the user
 */
export async function loginUser(username, password) {
    try {
        const response = await fetch(`${BASE_URL}/auth/login`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json',},
            body: JSON.stringify({ username, password }),
        });

        if (!response.ok) {
            const errorText = await response.text();
            throw new Error(errorText || 'Incorrect name or password.');
        }

        return await response.text();

    } catch (error) {
        console.error("Error during login process:", error);
        throw error;
    }
}

/**
 * Query to get racers based on argument searchQuery
 * @param searchQuery - search query
 * @returns {Promise<any>} - racers located in the DB based on the searchQuery
 */
export async function getRacers(searchQuery) {
    let url = `${BASE_URL}/racers`;

    if (searchQuery && searchQuery.trim() !== '') {
        url += `?search=${encodeURIComponent(searchQuery.trim())}`;
    }

    const response = await fetch(url);
    if (!response.ok) {
        throw new Error('Was not able to fetch racers.');
    }
    return await response.json();
}

/**
 * Bans a racer located using his ID
 * @param racerId - ID of the racer to ban
 * @returns {Promise<string>} - message indicating if the ban was successful
 */
export async function banRacer(racerId) {
    const response = await fetch(`${BASE_URL}/racers/${racerId}/ban`, {
        method: 'POST',
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || 'Was not able to ban the racer.');
    }
    return await response.text();
}


/**
 * Unbans a racer located using his ID
 * @param racerId - ID of the racer to unban
 * @returns {Promise<string>} - message indicating if the unban was successful
 */
export async function unbanRacer(racerId) {
    const response = await fetch(`${BASE_URL}/racers/${racerId}/unban`, {
        method: 'POST',
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || 'Was not able to unban the racer.');
    }
    return await response.text();
}

/**
 * Registers a new racer
 * @param racerData - data of the racer to register
 * @returns {Promise<string>} - message indicating if the registration was successful
 */
export async function registerRacer(racerData) {
    const response = await fetch(`${BASE_URL}/racers/register`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(racerData),
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || 'Registration unsuccessful.');
    }
    return await response.text();
}

/**
 * Creates a new race based on the provided data
 * @param raceData - data of the race to create
 * @returns {Promise<string>} - message indicating if the race was created successfully
 */
export async function createRace(raceData) {
    const response = await fetch(`${BASE_URL}/races/setup`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(raceData),
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || 'Error during race setup. Please try again later.');
    }

    return await response.text();
}

/**
 * Retrieves all races from the database
 * @returns {Promise<any>} - array of races
 */
export async function getRaces() {
    const response = await fetch(`${BASE_URL}/races`);
    if (!response.ok) {
        throw new Error('Error fetching races. Please try again later.');
    }
    return await response.json();
}

/**
 * Retrieves the results of a specific race
 * @param raceId - ID of the race to retrieve results for
 * @returns {Promise<any>} - array of results for the specified race
 */
export async function getRaceResults(raceId) {
    const response = await fetch(`${BASE_URL}/races/${raceId}/results`);
    if (!response.ok) {
        throw new Error('Error fetching race results. Please try again later.');
    }
    return await response.json();
}