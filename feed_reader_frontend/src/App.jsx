import { useState } from 'react'
import './App.css'

function App() {
  const [feedUrl, setFeedUrl] = useState('');
  const [articles, setArticles] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setArticles([]);
    if (!feedUrl.trim()) {
      setError('Please enter a feed URL.');
      return;
    }
    try {
      setLoading(true);
      const response = await fetch('http://localhost:8080/api/summarize', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ url: feedUrl })
      });
      console.log(response.status);
      
      if (!response.ok) {
        throw new Error('Failed to fetch summaries.');
      }
      const data = await response.json();
      setArticles(data.articles || []);
    } catch (err) {
      setError(err.message || 'Something went wrong.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container">
      <h1>Feed Summarizer</h1>
      <form className="feed-form" onSubmit={handleSubmit}>
        <input
          type="url"
          placeholder="Enter feed URL..."
          value={feedUrl}
          onChange={e => setFeedUrl(e.target.value)}
          required
        />
        <button type="submit" disabled={loading}>Summarize</button>
      </form>
      {error && <div className="error">{error}</div>}
      {loading && <div className="loading">Loading...</div>}
      <div className="articles">
        {articles.map((article, idx) => (
          <div className="article-card" key={idx}>
            <a href={article.link} target="_blank" rel="noopener noreferrer" className="article-title">
              {article.title}
            </a>
            <p className="article-summary">{article.summary}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default App
