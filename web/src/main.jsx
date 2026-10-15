import React, { useState } from 'react';
import { createRoot } from 'react-dom/client';
import './style.css';

function App() {
  const [signedIn, setSignedIn] = useState(false);
  const [card, setCard] = useState('');
  const [pin, setPin] = useState('');
  const [active, setActive] = useState('Overview');
  const [notice, setNotice] = useState('');
  const [amount, setAmount] = useState('');

  const signIn = (e) => {
    e.preventDefault();
    setNotice('Demo UI only — authentication is not connected to your MySQL database yet.');
  };
  const doAction = (action) => {
    setNotice(`${action} is a UI preview. Connect the Java API and MySQL database before using real account data.`);
    setActive(action);
  };

  if (!signedIn) return (
    <main className="login-shell">
      <section className="login-card">
        <div className="brand"><div className="brand-mark">N</div><span>NORTHSTAR <b>BANK</b></span></div>
        <div className="eyebrow">DIGITAL BANKING</div>
        <h1>Welcome back.</h1>
        <p className="muted">Sign in to preview your banking dashboard.</p>
        <form onSubmit={signIn}>
          <label htmlFor="card">Card number</label>
          <input id="card" value={card} onChange={e=>setCard(e.target.value)} placeholder="Enter your card number" autoComplete="off" />
          <label htmlFor="pin">ATM PIN</label>
          <input id="pin" type="password" value={pin} onChange={e=>setPin(e.target.value)} placeholder="••••" maxLength="6" autoComplete="off" />
          <button className="primary full" type="submit">Continue <span>→</span></button>
        </form>
        {notice && <div className="notice">{notice}</div>}
        <div className="secure-note"><span className="lock">⌑</span> Preview mode · no credentials are sent or stored</div>
      </section>
      <aside className="login-art">
        <div className="art-orbit orbit-one"></div><div className="art-orbit orbit-two"></div>
        <div className="art-copy"><span className="eyebrow">BANKING, SIMPLIFIED</span><h2>Your money.<br/>Your momentum.</h2><p>A modern interface for everyday banking.</p></div>
        <div className="floating-card"><span>◈ &nbsp; NORTHSTAR</span><strong>•••• &nbsp; •••• &nbsp; •••• &nbsp; 2048</strong><small>DEBIT CARD <b>VISA</b></small></div>
      </aside>
    </main>
  );

  return (
    <div className="app-shell">
      <aside className="sidebar">
        <div className="brand"><div className="brand-mark">N</div><span>NORTHSTAR <b>BANK</b></span></div>
        <div className="nav-label">WORKSPACE</div>
        {['Overview','Balance Enquiry','Deposit','Withdraw','Mini Statement','Change PIN'].map((item,i)=><button key={item} className={active===item?'nav-item selected':'nav-item'} onClick={()=>doAction(item)}><span>{['⌂','◉','↙','↗','▤','⚙'][i]}</span>{item}</button>)}
        <div className="sidebar-bottom"><div className="avatar">RV</div><div><strong>Demo User</strong><small>Personal account</small></div><button className="logout" onClick={()=>{setSignedIn(false);setNotice('')}} title="Sign out">↗</button></div>
      </aside>
      <main className="dashboard">
        <header className="topbar"><div><span className="eyebrow">PERSONAL BANKING</span><h1>{active}</h1></div><div className="top-right"><span className="status-dot"></span> Preview environment <div className="avatar">RV</div></div></header>
        <div className="notice persistent">This is a frontend preview. Login, balances and transactions are not connected to a live database.</div>
        <section className="welcome-row"><div><h2>Good to see you, Raman <span>✦</span></h2><p className="muted">Here’s your account snapshot.</p></div><button className="secondary" onClick={()=>doAction('Mini Statement')}>View statement ↗</button></section>
        <section className="summary-grid">
          <article className="balance-card"><div className="card-top"><span>AVAILABLE BALANCE</span><span className="balance-icon">₹</span></div><h2>₹ 24,850<span>.00</span></h2><p>Demo balance · not real account data</p><div className="balance-footer"><span>•••• 2048</span><span>↗ Account details</span></div></article>
          <article className="metric-card"><div className="metric-icon deposit-icon">↙</div><span className="metric-label">TOTAL DEPOSITS</span><h3>₹ 32,000</h3><small>Demo value</small></article>
          <article className="metric-card"><div className="metric-icon withdraw-icon">↗</div><span className="metric-label">TOTAL WITHDRAWALS</span><h3>₹ 7,150</h3><small>Demo value</small></article>
        </section>
        <section className="content-grid">
          <article className="panel actions-panel"><div className="panel-heading"><div><h3>Quick actions</h3><p className="muted">Choose a banking service</p></div><span className="panel-dots">•••</span></div><div className="action-grid">{[{name:'Deposit',icon:'↙',desc:'Add funds'},{name:'Withdraw',icon:'↗',desc:'Cash withdrawal'},{name:'Balance Enquiry',icon:'₹',desc:'Check balance'},{name:'Mini Statement',icon:'▤',desc:'Recent activity'}].map(a=><button className="action-tile" key={a.name} onClick={()=>doAction(a.name)}><span className="action-icon">{a.icon}</span><strong>{a.name}</strong><small>{a.desc}</small><span className="tile-arrow">↗</span></button>)}</div></article>
          <article className="panel transfer-panel"><div className="panel-heading"><div><h3>{active==='Deposit'||active==='Withdraw'?active:'Quick transaction'}</h3><p className="muted">Demo form — no money moves</p></div></div><label>Amount (₹)</label><div className="amount-input"><span>₹</span><input value={amount} onChange={e=>setAmount(e.target.value.replace(/[^0-9.]/g,''))} placeholder="0.00" inputMode="decimal"/></div><div className="amount-presets">{['500','1,000','2,000'].map(v=><button key={v} onClick={()=>setAmount(v.replace(',',''))}>₹{v}</button>)}</div><button className="primary full" onClick={()=>doAction(active==='Withdraw'?'Withdraw':active==='Deposit'?'Deposit':'Transaction')}>Continue <span>→</span></button></article>
        </section>
        <section className="panel transactions"><div className="panel-heading"><div><h3>Recent transactions</h3><p className="muted">Sample activity for layout preview</p></div><button className="text-button" onClick={()=>doAction('Mini Statement')}>See all ↗</button></div><div className="transaction-row"><div className="transaction-symbol green">↙</div><div className="transaction-name"><strong>Cash deposit</strong><small>Today · 10:42 AM</small></div><span className="tag success">Completed</span><strong className="positive">+ ₹5,000.00</strong></div><div className="transaction-row"><div className="transaction-symbol red">↗</div><div className="transaction-name"><strong>ATM withdrawal</strong><small>Yesterday · 04:18 PM</small></div><span className="tag success">Completed</span><strong>− ₹2,000.00</strong></div><div className="transaction-row"><div className="transaction-symbol blue">▤</div><div className="transaction-name"><strong>Monthly account service</strong><small>12 Oct 2026 · 09:00 AM</small></div><span className="tag success">Completed</span><strong>− ₹150.00</strong></div></section>
        {notice && <div className="notice toast" role="status">{notice}<button onClick={()=>setNotice('')}>×</button></div>}
        <footer>© 2026 Northstar Bank · Student project preview <span>Built for demonstration only</span></footer>
      </main>
    </div>
  );
}

createRoot(document.getElementById('root')).render(<App />);
