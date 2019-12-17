describe('Listar baños', function() {

    it('Debería listar los baños', function() {
        browser.get('dashboard/listarbano');

        let listaBanos = element.all(by.css('.card'));
        expect(listaBanos.count()).toBeGreaterThanOrEqual(0);
    })

});