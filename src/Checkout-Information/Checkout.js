document.addEventListener("DOMContentLoaded", function () {
  // --- ESTRATÉGIAS DE PAGAMENTO ---
  const paymentStrategies = {
    "credit-card": {
      pay: (data) => {
        console.log("Pagando com Cartão de Crédito:", data);
        // Lógica para processar pagamento com cartão de crédito
        alert("Pagamento com Cartão de Crédito realizado com sucesso!");
      },
      validate: () => {
        const cardNumber = document.getElementById("card-number").value;
        const cardName = document.getElementById("card-name").value;
        const cardExpiry = document.getElementById("card-expiry").value;
        const cardCvv = document.getElementById("card-cvv").value;
        if (!cardNumber || !cardName || !cardExpiry || !cardCvv) {
          alert("Por favor, preencha todos os dados do cartão.");
          return false;
        }
        return true;
      },
      collectData: () => ({
        cardNumber: document.getElementById("card-number").value,
        cardName: document.getElementById("card-name").value,
        cardExpiry: document.getElementById("card-expiry").value,
        cardCvv: document.getElementById("card-cvv").value,
        installments: document.getElementById("card-installments").value,
      }),
    },
    pix: {
      pay: (data) => {
        console.log("Pagando com Pix:", data);
        // Lógica para processar pagamento com Pix
        alert("Pagamento com Pix realizado com sucesso! (simulação)");
      },
      validate: () => {
        // Validação para Pix (se necessário)
        return true;
      },
      collectData: () => ({
        pixCode: document.querySelector("#pix-content .pix-code-group input").value,
      }),
    },
    boleto: {
      pay: (data) => {
        console.log("Pagando com Boleto:", data);
        // Lógica para gerar boleto
        alert("Boleto gerado com sucesso! (simulação)");
      },
      validate: () => {
        // Validação para Boleto (se necessário)
        return true;
      },
      collectData: () => ({
        message: "Boleto a ser gerado.",
      }),
    },
  };

  // --- CONTEXTO DE PAGAMENTO ---
  class PaymentContext {
    constructor(strategy) {
      this.strategy = strategy;
    }

    setStrategy(strategy) {
      this.strategy = strategy;
    }

    executePayment() {
      if (this.strategy.validate()) {
        const data = this.strategy.collectData();
        this.strategy.pay(data);
        // Aqui você pode redirecionar para a página de sucesso, etc.
      }
    }
  }

  // --- INICIALIZAÇÃO E EVENTOS ---

  // Seleciona os formulários e seções
  const formIdentificacao = document.getElementById("form-identificacao");
  const formEndereco = document.getElementById("form-endereco");
  const stepIdentificacao = document.getElementById("identificacao");
  const stepEndereco = document.getElementById("endereco");
  const stepPagamento = document.getElementById("pagamento");
  const btnFinishPayment = document.getElementById("btn-finish-payment");

  // Instancia o contexto de pagamento com a estratégia inicial (cartão de crédito)
  const paymentContext = new PaymentContext(paymentStrategies["credit-card"]);

  // Função para navegar entre as etapas
  function goToStep(targetStep) {
    document.querySelectorAll(".checkout-step").forEach((step) => {
      step.classList.remove("active");
    });
    if (targetStep) {
      targetStep.classList.add("active");
      targetStep.scrollIntoView({ behavior: "smooth", block: "start" });
    }
  }

  // Navegação entre etapas
  if (formIdentificacao) {
    formIdentificacao.addEventListener("submit", (event) => {
      event.preventDefault();
      goToStep(stepEndereco);
    });
  }

  if (formEndereco) {
    formEndereco.addEventListener("submit", (event) => {
      event.preventDefault();
      goToStep(stepPagamento);
    });
  }

  // Abas de tipo de cliente (PF/PJ)
  const customerTypeTabs = document.querySelectorAll(".customer-type-tabs .tab-btn");
  const customerForms = document.querySelectorAll(".customer-form");

  customerTypeTabs.forEach((tab) => {
    tab.addEventListener("click", () => {
      customerTypeTabs.forEach((item) => item.classList.remove("active"));
      customerForms.forEach((form) => form.classList.remove("active"));
      tab.classList.add("active");
      document.getElementById(tab.getAttribute("data-form")).classList.add("active");
    });
  });

  // Abas de método de pagamento
  const paymentTabs = document.querySelectorAll(".payment-method-tab");
  const paymentPanels = document.querySelectorAll(".payment-panel");

  paymentTabs.forEach((tab) => {
    tab.addEventListener("click", () => {
      paymentTabs.forEach((item) => item.classList.remove("active"));
      tab.classList.add("active");

      const targetId = tab.querySelector('input[type="radio"]').value;
      const strategy = paymentStrategies[targetId];

      if (strategy) {
        paymentContext.setStrategy(strategy); // Atualiza a estratégia no contexto
        console.log("Estratégia de pagamento alterada para:", targetId);
      }

      paymentPanels.forEach((panel) => panel.classList.remove("active"));
      document.getElementById(targetId + "-content").classList.add("active");
    });
  });

  // Botão de Finalizar Compra
  if (btnFinishPayment) {
    btnFinishPayment.addEventListener("click", () => {
      paymentContext.executePayment();
    });
  }
});
