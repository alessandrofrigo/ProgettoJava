const API_BASE = '/api';

document.addEventListener('DOMContentLoaded', () => {
    loadCategories();
    loadTransactions();

    document.getElementById('categoria').addEventListener('change', (e) => {
        const cat = e.target.value;
        const subCatSelect = document.getElementById('sottocategoria');
        if (cat) {
            loadSubCategories(cat);
        } else {
            subCatSelect.innerHTML = '<option value="">Seleziona prima categoria</option>';
            subCatSelect.disabled = true;
        }
    });

    document.getElementById('transaction-form').addEventListener('submit', (e) => {
        e.preventDefault();
        saveTransaction();
    });

    document.getElementById('btn-delete-options').addEventListener('click', () => {
        document.getElementById('delete-panel').classList.toggle('hidden');
    });
});

async function loadCategories() {
    try {
        const res = await fetch(`${API_BASE}/categorie`);
        const cats = await res.json();
        const select = document.getElementById('categoria');
        cats.forEach(c => {
            select.innerHTML += `<option value="${c}">${c}</option>`;
        });
    } catch (e) {
        console.error('Err loading categories:', e);
    }
}

async function loadSubCategories(cat) {
    try {
        const res = await fetch(`${API_BASE}/categorie/${cat}/sottocategorie`);
        const subCats = await res.json();
        const select = document.getElementById('sottocategoria');
        select.innerHTML = '<option value="">Seleziona...</option>';
        subCats.forEach(sc => {
            select.innerHTML += `<option value="${sc}">${sc}</option>`;
        });
        select.disabled = false;
    } catch (e) {
        console.error('Err subcats', e);
    }
}

async function loadTransactions() {
    try {
        const res = await fetch(`${API_BASE}/transazioni`);
        const data = await res.json();
        const container = document.getElementById('transactions-container');
        container.innerHTML = '';
        
        let total = 0;

        data.forEach(t => {
            total += t.importo;
            const cat = typeof t.categoria === 'string' ? t.categoria : (t.categoriaStr || JSON.stringify(t.categoria));
            const sub = t.sottocategoria;
            
            container.innerHTML += `
               <div class="transaction-item">
                    <div class="ts-info">
                        <h4>${t.descrizione}</h4>
                        <div class="ts-meta">
                            <span>${cat}</span>
                            <span>${sub}</span>
                            <span>${t.data}</span>
                        </div>
                    </div>
                    <div class="ts-right">
                        <div class="ts-amount">${t.importo.toFixed(2)} €</div>
                        <button class="btn-delete" title="Elimina" onclick="deleteById(${t.id})">✖</button>
                    </div>
               </div>
            `;
        });

        document.getElementById('total-amount').innerText = total.toFixed(2) + ' €';

    } catch (e) {
        console.error('Err loading transactions', e);
    }
}

async function saveTransaction() {
    const data = {
        descrizione: document.getElementById('descrizione').value,
        categoria: document.getElementById('categoria').value,
        sottocategoria: document.getElementById('sottocategoria').value,
        importo: parseFloat(document.getElementById('importo').value),
        data: document.getElementById('data').value
    };

    try {
        await fetch(`${API_BASE}/transazioni`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });

        document.getElementById('transaction-form').reset();
        document.getElementById('sottocategoria').disabled = true;
        loadTransactions();
    } catch (e) {
        console.error('Err saving trans', e);
    }
}

async function deleteById(id) {
    if(!confirm('Sicuro di voler eliminare questa transazione?')) return;
    try {
        await fetch(`${API_BASE}/transazioni/${id}`, { method: 'DELETE' });
        loadTransactions();
    } catch (e) { console.error(e); }
}

async function deleteByCategoria() {
    const cat = document.getElementById('del-cat').value;
    if(!cat || !confirm(`Eliminare tutte le spese di: ${cat}?`)) return;
    try {
        await fetch(`${API_BASE}/transazioni/categoria/${cat}`, { method: 'DELETE' });
        document.getElementById('del-cat').value = '';
        loadTransactions();
    } catch (e) { console.error(e); }
}

async function deleteByDate() {
    const date = document.getElementById('del-date').value;
    if(!date || !confirm(`Eliminare tutte le spese precedenti a: ${date}?`)) return;
    try {
        await fetch(`${API_BASE}/transazioni/data/${date}`, { method: 'DELETE' });
        document.getElementById('del-date').value = '';
        loadTransactions();
    } catch (e) { console.error(e); }
}
