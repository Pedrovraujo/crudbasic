const API_BASE = "http://localhost:8080";

async function carregarServicos() {
  const select = document.getElementById("servicoSelect");
  const descricao = document.getElementById("descricaoServico");
  select.innerHTML = "<option>Carregando...</option>";

  try {
    const resp = await fetch(`${API_BASE}/servicos`);
    const dados = await resp.json();

    select.innerHTML = "<option value=''>Selecione um serviço</option>";

    dados.forEach(servico => {
      const opt = document.createElement("option");
      const texto = servico.descricao
        ? `${servico.nomeTrabalho} - ${servico.descricao.trim()}`
        : servico.nomeTrabalho;
      opt.value = servico.id;
      opt.textContent = texto;
      opt.dataset.descricao = servico.descricao || "";
      select.appendChild(opt);
    });


    select.addEventListener("change", () => {
      const selected = select.options[select.selectedIndex];
      const texto = selected.dataset.descricao?.trim();
      descricao.textContent = texto ? `Descrição: ${texto}` : "";
    });

    descricao.textContent = "";
  } catch (e) {
    console.error("Erro ao carregar serviços", e);
    select.innerHTML = "<option>Erro ao carregar serviços</option>";
  }
}

async function cadastrarOuEditarPessoa(e) {
  e.preventDefault();
  const id = document.getElementById("idPessoa").value;
  const metodo = id ? "PUT" : "POST";
  const url = id ? `${API_BASE}/pessoas/${id}` : `${API_BASE}/pessoas`;

  const pessoa = {
    nome: document.getElementById("nome").value,
    idade: parseInt(document.getElementById("idade").value),
    servicoId: parseInt(document.getElementById("servicoSelect").value),
    dataAtendimento: document.getElementById("dataAtendimento").value,
    status: document.getElementById("status").value
  };

  try {
    const resp = await fetch(url, {
      method: metodo,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(pessoa)
    });

    if (resp.ok) {
      showToast(id ? "✅ Pessoa editada com sucesso!" : "✅ Pessoa cadastrada com sucesso!");
      e.target.reset();
      document.getElementById("idPessoa").value = "";
      document.getElementById("btnCadastrar").textContent = "Cadastrar";
      document.getElementById("btnCancelar").style.display = "none";
      document.getElementById("dataConsulta").value = document.getElementById("dataAtendimento").value;
      buscarPorData();

    } else {
      showToast("❌ Erro ao salvar pessoa.", false);
    }
  } catch (err) {
    showToast("❌ Erro ao conectar com o servidor.", false);
  }
}


async function buscarPorData() {
  const data = document.getElementById("dataConsulta").value;
  const tbody = document.querySelector("#tabelaPessoas tbody");
  const msgLista = document.getElementById("msgLista");

  tbody.innerHTML = "";
  msgLista.textContent = "";

  if (!data) {
    msgLista.textContent = "⚠️ Selecione uma data.";
    msgLista.className = "error";
    return;
  }

  msgLista.textContent = "🔄 Carregando...";
  msgLista.className = "";

  try {
    const resp = await fetch(`${API_BASE}/pessoas/data?data=${data}`);
    if (!resp.ok) throw new Error(`Erro HTTP ${resp.status}`);
    const pessoas = await resp.json();

    tbody.innerHTML = "";

    if (pessoas.length === 0) {
      msgLista.textContent = "Nenhum agendamento encontrado para essa data.";
      msgLista.className = "error";
      return;
    }

   pessoas.forEach(p => {
     const tr = document.createElement("tr");
     tr.innerHTML = `
       <td>${p.nome}</td>
       <td>${p.idade}</td>
       <td>${p.nomeServico}${p.descricaoServico ? " - " + p.descricaoServico : ""}</td>
       <td>${p.status ? p.status : "PENDENTE"}</td> <!-- 🔹 exibe o status -->
       <td>
         <button class="btn-action btn-edit" onclick="editarPessoa(${p.id})"><i class="ri-edit-line"></i></button>
         <button class="btn-action btn-delete" onclick="removerPessoa(${p.id})"><i class="ri-delete-bin-line"></i></button>
       </td>
     `;
     tbody.appendChild(tr);
   });



    msgLista.textContent = `${pessoas.length} pessoa(s) encontradas para ${data}`;
    msgLista.className = "success";
  } catch (err) {
    console.error(err);
    msgLista.textContent = "Erro ao buscar agendamentos.";
    msgLista.className = "error";
  }
}

async function editarPessoa(id) {
  const resp = await fetch(`${API_BASE}/pessoas/${id}`);
  const p = await resp.json();

  document.getElementById("idPessoa").value = p.id;
  document.getElementById("nome").value = p.nome;
  document.getElementById("idade").value = p.idade;
  document.getElementById("dataAtendimento").value = document.getElementById("dataConsulta").value;
  document.getElementById("btnCadastrar").textContent = "Salvar Alterações";
  document.getElementById("btnCancelar").style.display = "inline-block";
  window.scrollTo({ top: 0, behavior: "smooth" });
}

async function removerPessoa(id) {
  if (!confirm("Tem certeza que deseja excluir esta pessoa?")) return;

  const resp = await fetch(`${API_BASE}/pessoas/${id}`, { method: "DELETE" });
  if (resp.ok) {
    showToast("Pessoa removida com sucesso!");
    buscarPorData();
  } else {
    showToast("Erro ao remover pessoa!");
  }
}

document.getElementById("formPessoa").addEventListener("submit", cadastrarOuEditarPessoa);
document.getElementById("dataConsulta").addEventListener("change", buscarPorData);
document.getElementById("btnCancelar").addEventListener("click", () => {
  document.getElementById("formPessoa").reset();
  document.getElementById("idPessoa").value = "";
  document.getElementById("btnCadastrar").textContent = "Cadastrar";
  document.getElementById("btnCancelar").style.display = "none";
});

carregarServicos();

function showToast(msg, success = true) {
  const toast = document.createElement("div");
  toast.className = "toast";
  toast.style.background = success ? "#16a34a" : "#dc2626";
  toast.textContent = msg;
  document.body.appendChild(toast);
  setTimeout(() => toast.classList.add("show"), 100);
  setTimeout(() => {
    toast.classList.remove("show");
    setTimeout(() => toast.remove(), 300);
  }, 3000);
}

