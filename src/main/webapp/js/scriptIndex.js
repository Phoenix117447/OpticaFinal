function checkInputs() {
    const nombre = document.getElementById('nombre').value.trim();
    const correo = document.getElementById('correo').value.trim();
    const telefono = document.getElementById('telefono').value.trim();
    const boton = document.getElementById('agendar');

    // Habilitar el botón solo si todos los campos tienen valor
    if (nombre && correo && telefono) {
        boton.classList.add('active');
        boton.removeAttribute('disabled');
    } else {
        boton.classList.remove('active');
        boton.setAttribute('disabled', 'true');
    }
}