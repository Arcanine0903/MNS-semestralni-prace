export default function HomePage() {
    const newsItems = [1, 2, 3];

    return (
        <div style={{ padding: '40px 5%', width: '100%' }}>
            <h1 style={{ textAlign: 'center', marginBottom: '60px', fontWeight: 'normal' }}>
                What's New!
            </h1>

            <div style={{
                display: 'flex',
                justifyContent: 'space-between',
                gap: '40px',
                flexWrap: 'wrap'
            }}>

                {newsItems.map((item) => (
                    <div key={item} style={{ flex: '1', minWidth: '250px' }}>
                        <svg width="250" height="250" style={{ display: 'block', margin: '0 auto 20px auto' }}>
                            <rect width="100%" height="100%" fill="transparent" stroke="#555" strokeWidth="2" />
                            <line x1="0" y1="0" x2="100%" y2="100%" stroke="#555" strokeWidth="2" />
                            <line x1="100%" y1="0" x2="0" y2="100%" stroke="#555" strokeWidth="2" />
                        </svg>
                        <h2 style={{ marginBottom: '15px' }}>Heading</h2>
                        <p style={{ color: '#ccc', lineHeight: '1.5' }}>
                            Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                            tempor incididunt ut labore et dolore magna aliqua.
                        </p>
                    </div>
                ))}

            </div>
        </div>
    );
}