const BASE_URL = 'http://localhost:8080/api';

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

        const data = await response.text();
        return data;

    } catch (error) {
        console.error("Error during login process:", error);
        throw error;
    }
}

export async function getRacers() {
    const response = await fetch(`${BASE_URL}/racers`);
    if (!response.ok) {
        throw new Error('Was not able to fetch racers.');
    }
    return await response.json();
}

export async function toggleBanRacer(racerId) {
    const response = await fetch(`${BASE_URL}/racers/${racerId}/ban`, {
        method: 'POST',
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || 'Was not able to ban the racer.');
    }
    return await response.text();
}

export async function toggleUnbanRacer(racerId) {
    const response = await fetch(`${BASE_URL}/racers/${racerId}/unban`, {
        method: 'POST',
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || 'Was not able to unban the racer.');
    }
    return await response.text();
}