describe('Crear baño', function() {

    it('Debería crear un baño', function() {
        browser.get('http://localhost:4200/dashboard/crearbano');
        let campoIdentificador = element(by.id('identificador'));
        let nombreBano = 'Baño ' + Math.random().toString(36).substring(2, 7);
        campoIdentificador.sendKeys(nombreBano);

        expect(campoIdentificador.getAttribute('value')).toBe(nombreBano);
        let formulario = element(by.id('formulario'));
        formulario.submit();

        let mensajeExitoso = element(by.id('mensaje'));
        expect(mensajeExitoso.getText()).toEqual('El registro fue creado exitosamente');
    })

});