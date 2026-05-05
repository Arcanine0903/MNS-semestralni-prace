import { Link } from 'react-router-dom';

export default function Button({ text, to }) {
    return (
        <Link to={to} style={{ textDecoration: 'none' }}>
            <button style={{
                backgroundColor: '#0084ff',
                color: 'white',
                border: 'none',
                padding: '10px 24px',
                borderRadius: '8px',
                fontSize: '16px',
                fontWeight: 'bold',
                cursor: 'pointer',
                marginLeft: '15px'
            }}>
                {text}
            </button>
        </Link>
    );
}