/**
 * THEME TOGGLE - Controle de Modo Escuro/Claro
 * Sistema de alternância de tema com persistência no localStorage
 */

(function() {
    'use strict';

    // Chave para armazenar preferência no localStorage
    const THEME_KEY = 'npa-theme-preference';
    const htmlElement = document.documentElement;
    
    /**
     * Obtém o tema salvo ou usa padrão claro
     */
    function getSavedTheme() {
        const savedTheme = localStorage.getItem(THEME_KEY);
        if (savedTheme) {
            return savedTheme;
        }
        // Padrão: tema claro (não detecta preferência do SO para evitar confusão)
        return 'light';
    }
    
    /**
     * Aplica o tema na página
     */
    function applyTheme(theme) {
        console.log('Aplicando tema:', theme);
        
        if (theme === 'dark') {
            htmlElement.setAttribute('data-theme', 'dark');
        } else {
            htmlElement.removeAttribute('data-theme');
        }
        
        // Atualiza o checkbox
        const themeToggle = document.getElementById('theme-toggle');
        if (themeToggle) {
            themeToggle.checked = (theme === 'dark');
        }
        
        // Salva preferência
        localStorage.setItem(THEME_KEY, theme);
    }
    
    /**
     * Alterna entre os temas
     */
    function toggleTheme() {
        const currentTheme = htmlElement.getAttribute('data-theme');
        const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
        console.log('Alternando de', currentTheme || 'light', 'para', newTheme);
        applyTheme(newTheme);
    }
    
    /**
     * Inicialização
     */
    function init() {
        console.log('Inicializando theme toggle...');
        
        // Aplica tema salvo
        const savedTheme = getSavedTheme();
        console.log('Tema salvo:', savedTheme);
        applyTheme(savedTheme);
        
        // Adiciona listener ao toggle switch
        const themeToggle = document.getElementById('theme-toggle');
        if (themeToggle) {
            console.log('Toggle encontrado, adicionando listener');
            themeToggle.addEventListener('change', function() {
                console.log('Toggle clicado!');
                toggleTheme();
            });
        } else {
            console.warn('Toggle não encontrado no DOM');
        }
    }
    
    // Aplica tema imediatamente (antes do DOMContentLoaded) para evitar flash
    const initialTheme = getSavedTheme();
    if (initialTheme === 'dark') {
        htmlElement.setAttribute('data-theme', 'dark');
    } else {
        htmlElement.removeAttribute('data-theme');
    }
    
    // Executa inicialização quando o DOM estiver pronto
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        // DOM já está pronto
        init();
    }
})();
