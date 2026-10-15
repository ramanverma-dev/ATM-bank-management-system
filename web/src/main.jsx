import React, { useEffect, useState } from 'react';
import { createRoot } from 'react-dom/client';
import './style.css';

const API = (import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080').replace(/\/$/, '');
async function request(path, options = {}) {
  const response = await fetch(`${API}/api${path}`, {
    credentials: 'include',
    headers: { 'Content-Type': 'application/json', ...(options.headers || {}) },
    ...options,
  });
  const data = await response.json().catch(() => ({}));
  if (!response.ok) throw new Error(data.message || data.error || `Request failed (${response.status})`);
  return data;
}
const money = (value) => new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR', maximumFractionDigits: 2 }).format(Number(value || 0));

function App() {
  const [account, setAccount] = useState(null);
  const [transactions, setTransactions] = useState([]);
  const [mode, setMode] = useState('login');
  const [card, setCard] = useState('');
  const [pin, setPin] = useState('');
  const [name, setName] = useState('');
  const [active, setActive] = useState('Overview');
  const [amount, setAmount] = useState('');
  const [currentPin, setCurrentPin] = useState('');
  const [newPin, setNewPin] = useState('');
  const [notice, setNotice] = useState('');
  const [error, setError] = useState('');
  const [busy, setBusy] = useState(false);

  async function refresh() {
    const a = await request('/account');
    setAccount(a);
    const tx = await request('/account/transactions');
    setTransactions(tx);
  }
  useEffect(() => { refresh().catch(() => {}); }, []);

  async function submitAuth(e) {
    e.preventDefault(); setError(''); setNotice(''); setBusy(true);
    try {
      if (mode === 'signup') {
        if (!name.trim()) throw new Error('Please enter your name.');
        const result = await request('/auth/signup', { method: 'POST', body: JSON.stringify({ name: name.trim(), pin }) });
        setCard(result.cardNumber);
        setNotice(`Account created! Save your card number: ${result.cardNumber}`);
      } else {
        await request('/auth/login', { method: 'POST', body: JSON.stringify({ cardNumber: card.trim(), pin }) });
      }
      await refresh();
    } catch (e) { setError(e.message); }
    finally { setBusy(false); }
  }

  async function logout() {
    try { await request('/auth/logout', { method: 'POST', body: '{}' }); } catch {}
    setAccount(null); setTransactions([]); setPin(''); setNotice('You have signed out.'); setError('');
  }

  async function transact(type) {
    setError(''); setNotice(''); setBusy(true);
    try {
      const value = Number(amount);
      if (!Number.isFinite(value) || value <= 0) throw new Error('Enter an amount greater than zero.');
      const result = await request(`/account/${type}`, { method: 'POST', body: JSON.stringify({ amount: value }) });
      setNotice(result.message); setAmount(''); await refresh();
    } catch (e) { setError(e.message); }
    finally { setBusy(false); }
  }

  async function changePin(e) {
    e.preventDefault(); setError(''); setNotice(''); setBusy(true);
    try {
      const result = await request('/account/pin', { method: 'POST', body: JSON.stringify({ currentPin, newPin }) });
      setNotice(result.message); setCurrentPin(''); setNewPin('');
    } catch (e) { setError(e.message); }
    finally { setBusy(false); }
  }

  if (!account) return (
    <main className="login-shell">
      <section className="login-card">
        <div className="brand"><div className="brand-mark">N</div><span>NORTHSTAR <b>BANK</b></span></div>
        <div className="eyebrow">DIGITAL BANKING</div>
        <h1>{mode === 'login' ? 'Welcome back.' : 'Open your account.'}</h1>
        <p className="muted">{mode === 'login' ? 'Sign in to your bank account.' : 'Create a demo account and receive a card number.'}</p>
        <form onSubmit={submitAuth}>
          {mode === 'signup' && <><label htmlFor="name">Full name</label><input id="name" value={name} onChange={e=>setName(e.target.value)} placeholder="Your name" maxLength="80" required /></>}
          {mode === 'login' && <><label htmlFor="card">16-digit card number</label><input id="card" value={card} onChange={e=>setCard(e.target.value.replace(/\D/g,'').slice(0,16))} placeholder="Enter your card number" inputMode="numeric" maxLength="16" required /></>}
          <label htmlFor="pin">{mode === 'signup' ? 'Choose a 4–6 digit PIN' : 'ATM PIN'}</label>
          <input id="pin" type="password" value={pin} onChange={e=>setPin(e.target.value.replace(/\D/g,'').slice(0,6))} placeholder="••••" inputMode="numeric" minLength="4" maxLength="6" required />
          <button className="primary full" type="submit" disabled={busy}>{busy ? 'Please wait…' : mode === 'login' ? 'Sign in' : 'Create account'} <span>→</span></button>
        </form>
        <button className="text-button auth-switch" onClick={()=>{setMode(mode==='login'?'signup':'login');setError('');setNotice('')}}>{mode==='login' ? 'New here? Create an account' : 'Already have an account? Sign in'}</button>
        {notice && <div className="notice">{notice}</div>}{error && <div className="error-box" role="alert">{error}</div>}
        <div className="secure-note"><span className="lock">⌑</span> Connected to the configured API · demo project only</div>
      </section>
      <aside className="login-art"><div className="art-orbit orbit-one"></div><div className="art-orbit orbit-two"></div><div className="art-copy"><span className="eyebrow">BANKING, SIMPLIFIED</span><h2>Your money.<br/>Your momentum.</h2><p>Deposits, withdrawals and statements in one place.</p></div><div className="floating-card"><span>◈ &nbsp; NORTHSTAR</span><strong>•••• &nbsp; •••• &nbsp; •••• &nbsp; {card ? card.slice(-4) : '••••'}</strong><small>DEBIT CARD <b>DEMO</b></small></div></aside>
    </main>
  );

  const pageTitle = active === 'Overview' ? 'Overview' : active;
  return (
    <div className="app-shell">
      <aside className="sidebar"><div className="brand"><div className="brand-mark">N</div><span>NORTHSTAR <b>BANK</b></span></div><div className="nav-label">WORKSPACE</div>
        {['Overview','Balance Enquiry','Deposit','Withdraw','Mini Statement','Change PIN'].map((item,i)=><button key={item} className={active===item?'nav-item selected':'nav-item'} onClick={()=>{setActive(item);setNotice('');setError('')}}><span>{['⌂','◉','↙','↗','▤','⚙'][i]}</span>{item}</button>)}
        <div className="sidebar-bottom"><div className="avatar">{(account.name||'U').slice(0,2).toUpperCase()}</div><div><strong>{account.name}</strong><small>Account holder</small></div><button className="logout" onClick={logout} title="Sign out">↗</button></div>
      </aside>
      <main className="dashboard">
        <header className="topbar"><div><span className="eyebrow">PERSONAL BANKING</span><h1>{pageTitle}</h1></div><div className="top-right"><span className="status-dot"></span> Connected <div className="avatar">{(account.name||'U').slice(0,2).toUpperCase()}</div></div></header>
        {notice && <div className="notice persistent" role="status">{notice}</div>}{error && <div className="error-box persistent" role="alert">{error}</div>}
        {active==='Overview' && <>
          <section className="welcome-row"><div><h2>Good to see you, {account.name} <span>✦</span></h2><p className="muted">Here’s your account snapshot from MySQL.</p></div><button className="secondary" onClick={()=>setActive('Mini Statement')}>View statement ↗</button></section>
          <section className="summary-grid"><article className="balance-card"><div className="card-top"><span>AVAILABLE BALANCE</span><span className="balance-icon">₹</span></div><h2>{money(account.balance)}</h2><p>Live balance from your demo account</p><div className="balance-footer"><span>•••• {account.cardNumber.slice(-4)}</span><span>Account active</span></div></article>
            <article className="metric-card"><div className="metric-icon deposit-icon">↙</div><span className="metric-label">TOTAL DEPOSITS</span><h3>{money(transactions.filter(t=>t.type==='DEPOSIT').reduce((s,t)=>s+Number(t.amount),0))}</h3><small>Recent 10 transactions</small></article>
            <article className="metric-card"><div className="metric-icon withdraw-icon">↗</div><span className="metric-label">TOTAL WITHDRAWALS</span><h3>{money(transactions.filter(t=>t.type==='WITHDRAWAL').reduce((s,t)=>s+Number(t.amount),0))}</h3><small>Recent 10 transactions</small></article></section>
          <section className="content-grid"><article className="panel actions-panel"><div className="panel-heading"><div><h3>Quick actions</h3><p className="muted">Choose a banking service</p></div><span className="panel-dots">•••</span></div><div className="action-grid">{[{name:'Deposit',icon:'↙',desc:'Add funds'},{name:'Withdraw',icon:'↗',desc:'Cash withdrawal'},{name:'Balance Enquiry',icon:'₹',desc:'Check balance'},{name:'Mini Statement',icon:'▤',desc:'Recent activity'}].map(a=><button className="action-tile" key={a.name} onClick={()=>setActive(a.name)}><span className="action-icon">{a.icon}</span><strong>{a.name}</strong><small>{a.desc}</small><span className="tile-arrow">↗</span></button>)}</div></article>
            <article className="panel transfer-panel"><div className="panel-heading"><div><h3>Quick transaction</h3><p className="muted">A real entry in your demo account</p></div></div><label>Amount (₹)</label><div className="amount-input"><span>₹</span><input value={amount} onChange={e=>setAmount(e.target.value.replace(/[^0-9.]/g,''))} placeholder="0.00" inputMode="decimal"/></div><div className="amount-presets">{['500','1000','2000'].map(v=><button key={v} onClick={()=>setAmount(v)}>₹{Number(v).toLocaleString('en-IN')}</button>)}</div><div className="quick-buttons"><button className="primary" onClick={()=>transact('deposit')} disabled={busy}>Deposit</button><button className="secondary" onClick={()=>transact('withdraw')} disabled={busy}>Withdraw</button></div></article></section>
        </>}
        {(active==='Deposit'||active==='Withdraw') && <section className="panel form-panel"><h3>{active} funds</h3><p className="muted">This will update your demo account balance and transaction history.</p><form onSubmit={e=>{e.preventDefault();transact(active==='Deposit'?'deposit':'withdraw')}}><label>Amount (₹)</label><div className="amount-input"><span>₹</span><input value={amount} onChange={e=>setAmount(e.target.value.replace(/[^0-9.]/g,''))} placeholder="Enter amount" inputMode="decimal" required/></div><button className="primary full" disabled={busy}>{busy?'Processing…':active}</button></form></section>}
        {active==='Balance Enquiry' && <section className="balance-card detail-balance"><div className="card-top"><span>AVAILABLE BALANCE</span><span className="balance-icon">₹</span></div><h2>{money(account.balance)}</h2><p>Current balance stored in the database.</p><button className="secondary" onClick={()=>refresh().catch(e=>setError(e.message))}>Refresh balance ↻</button></section>}
        {active==='Change PIN' && <section className="panel form-panel"><h3>Change ATM PIN</h3><p className="muted">Choose a new PIN with 4–6 digits.</p><form onSubmit={changePin}><label>Current PIN</label><input type="password" inputMode="numeric" value={currentPin} onChange={e=>setCurrentPin(e.target.value.replace(/\D/g,'').slice(0,6))} required minLength="4"/><label>New PIN</label><input type="password" inputMode="numeric" value={newPin} onChange={e=>setNewPin(e.target.value.replace(/\D/g,'').slice(0,6))} required minLength="4"/><button className="primary full" disabled={busy}>Update PIN</button></form></section>}
        {active==='Mini Statement' && <section className="panel transactions"><div className="panel-heading"><div><h3>Recent transactions</h3><p className="muted">Latest 10 records from MySQL</p></div><button className="text-button" onClick={()=>refresh().catch(e=>setError(e.message))}>Refresh ↻</button></div>{transactions.length===0?<p className="muted">No transactions yet. Make a deposit to get started.</p>:transactions.map(t=><div className="transaction-row" key={t.id}><div className={'transaction-symbol '+(t.type==='DEPOSIT'?'green':'red')}>{t.type==='DEPOSIT'?'↙':'↗'}</div><div className="transaction-name"><strong>{t.type==='DEPOSIT'?'Cash deposit':'ATM withdrawal'}</strong><small>{new Date(t.createdAt).toLocaleString()}</small></div><span className="tag success">Saved</span><strong className={t.type==='DEPOSIT'?'positive':''}>{t.type==='DEPOSIT'?'+':'−'} {money(t.amount)}</strong></div>)}</section>}
        {active!=='Mini Statement' && <section className="panel transactions"><div className="panel-heading"><div><h3>Recent transactions</h3><p className="muted">Latest activity for this account</p></div><button className="text-button" onClick={()=>setActive('Mini Statement')}>See all ↗</button></div>{transactions.slice(0,3).length===0?<p className="muted">No transactions yet.</p>:transactions.slice(0,3).map(t=><div className="transaction-row" key={t.id}><div className={'transaction-symbol '+(t.type==='DEPOSIT'?'green':'red')}>{t.type==='DEPOSIT'?'↙':'↗'}</div><div className="transaction-name"><strong>{t.type==='DEPOSIT'?'Cash deposit':'ATM withdrawal'}</strong><small>{new Date(t.createdAt).toLocaleString()}</small></div><span className="tag success">Saved</span><strong className={t.type==='DEPOSIT'?'positive':''}>{t.type==='DEPOSIT'?'+':'−'} {money(t.amount)}</strong></div>)}</section>}
        <footer>© 2026 Northstar Bank · Educational demo <span>Never use real banking credentials or money</span></footer>
      </main>
    </div>
  );
}
createRoot(document.getElementById('root')).render(<App />);
